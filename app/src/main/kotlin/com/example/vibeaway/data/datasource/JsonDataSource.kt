package com.example.vibeaway.data.datasource

abstract class JsonDataSource {

    /**
     * Returns a json string from a certain file at [path].
     * Should be a json file saved under resources folder.
     */
    fun getJson(path: String): String {
        val inputStream = requireNotNull(
            value = Thread.currentThread().contextClassLoader?.getResourceAsStream(path),
            lazyMessage = { "Path $path not found in resources." }
        )

        return inputStream.bufferedReader().use { it.readText() }
    }
}
