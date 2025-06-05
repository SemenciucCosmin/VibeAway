package com.example.vibeaway.data.core.datasource

import android.content.Context
import java.io.IOException

/**
 * Data source for managing access to json filed.
 */
abstract class JsonDataSource {

    /**
     * Returns the content of a json file specified at [path] as a [String].
     */
    fun getJsonFromResources(path: String): String {
        val inputStream = requireNotNull(
            value = Thread.currentThread().contextClassLoader?.getResourceAsStream(path),
            lazyMessage = { "Path $path not found in resources." }
        )

        return inputStream.bufferedReader().use { it.readText() }
    }

    fun getJson(context: Context, fileName: String): String? {
        return try {
            context.openFileInput(fileName).bufferedReader().use { it.readText() }
        } catch (_: IOException) {
            null
        }
    }

    fun writeJson(context: Context, fileName: String, json: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
            output.write(json.toByteArray())
        }
    }
}
