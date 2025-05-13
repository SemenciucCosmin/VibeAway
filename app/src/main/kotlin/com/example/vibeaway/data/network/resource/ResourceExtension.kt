package com.example.vibeaway.data.network.resource

import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.CancellationException

private const val HTTP_UNPROCESSABLE_ENTITY = 422

/**
 * Post process and get the Retrofit result body as [Resource].
 * If the result cannot be get then returns [Resource] with with specific error status.
 */
suspend fun <T> processApiResource(operation: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = operation.invoke()
        if (response.isSuccessful) {
            // Success case.
            val payload = response.body()
            if (!payload.isNullOrEmpty()) {
                Resource(payload, Status.Success)
            } else {
                Resource(payload, Status.Empty)
            }
        } else if (response.code() in arrayOf(
                HttpURLConnection.HTTP_UNAUTHORIZED,
                HttpURLConnection.HTTP_FORBIDDEN
            )
        ) {
            // Not authorized.
            Resource<T>(null, Status.ResourceAuthorizationError)
        } else if (response.code() == HTTP_UNPROCESSABLE_ENTITY) {
            // Invalid data
            Resource<T>(null, Status.ResourceGone)
        } else if (response.code() >= HttpURLConnection.HTTP_INTERNAL_ERROR) {
            // Not authorized.
            Resource<T>(null, Status.ResourceServerError)
        } else if (response.code() == HttpURLConnection.HTTP_GONE) {
            // Resource Gone.
            Resource<T>(null, Status.ResourceGone)
        } else if (response.code() >= HttpURLConnection.HTTP_NOT_FOUND) {
            // Not found.
            Resource<T>(null, Status.ResourceNotFoundError)
        } else {
            // Another http error code.
            Resource<T>(null, Status.ResourceAccessError)
        }
    } catch (exception: CancellationException) {
        // We want only to log this exception event.
        // Throw it back in order not to intervene with the native coroutine flow or work.
        throw exception
    } catch (_: IOException) {
        // Error occurred while communicating to the server.
        Resource(null, Status.ResourceNetworkError)
    } catch (_: Throwable) {
        // An internal error occurred while attempting to execute a request.
        Resource(null, Status.ResourceAccessError)
    }
}

/**
 * Checks if the data is null or empty.
 *
 * Empty is handled by type of object.
 */
internal fun Any?.isNullOrEmpty() = when {
    this == null -> true
    this is Collection<*> -> this.isEmpty()
    this is String -> this.isBlank()
    else -> false
}
