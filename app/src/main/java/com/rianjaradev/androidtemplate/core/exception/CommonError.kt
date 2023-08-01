package com.rianjaradev.androidtemplate.core.exception

sealed interface CommonError
data class ServerError(val code: String, val message: String) : CommonError
data class NetworkError(val message: String) : CommonError
