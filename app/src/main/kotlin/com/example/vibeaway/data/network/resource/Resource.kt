package com.example.vibeaway.data.network.resource

/**
 * Data class that acts as a wrapper over some retrieved data
 * @param [payload]: actual retrieved data
 * @param [status]: status of the retrieval process
 */
data class Resource<T>(val payload: T?, val status: Status)
