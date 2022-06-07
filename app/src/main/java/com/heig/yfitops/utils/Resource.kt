package com.heig.yfitops.utils

/**
 * This class is a object wrapper used for handling
 * success data and error callback responses from an API
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)

        fun <T> error(message: String, data: T?) = Resource(Status.ERROR, data, message)
    }
}

enum class Status {
    SUCCESS,
    ERROR
}