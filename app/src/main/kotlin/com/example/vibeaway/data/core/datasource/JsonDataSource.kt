package com.example.vibeaway.data.core.datasource

/**
 * Data source for managing access to json filed.
 */
abstract class JsonDataSource {

    /**
     * Returns the content of a json file specified at [path] as a [String].
     */
    fun getJson(path: String): String {
        val inputStream = requireNotNull(
            value = Thread.currentThread().contextClassLoader?.getResourceAsStream(path),
            lazyMessage = { "Path $path not found in resources." }
        )

        return inputStream.bufferedReader().use { it.readText() }
    }
}
