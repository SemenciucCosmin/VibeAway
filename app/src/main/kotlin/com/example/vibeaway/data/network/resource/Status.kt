package com.example.vibeaway.data.network.resource

/**
 * Sealed class for all possible data retrieval process outcomes.
 */
sealed class Status {
    data object Loading : Status()

    data object Success : Status()

    data object Empty : Status()

    data object ResourceNotFoundError : Status()

    data object ResourceAccessError : Status()

    data object ResourceNetworkError : Status()

    data object ResourceAuthorizationError : Status()

    data object ResourceServerError : Status()

    data object ResourceGone : Status()

    companion object {
        fun Status.isSuccess() = this is Success

        fun Status.isEmpty() = this is Empty

        fun Status.isSuccessOrEmpty() = this is Success || this is Empty

        fun Status.isError(): Boolean = !this.isSuccessOrEmpty()
    }
}
