package com.rianjaradev.androidtemplate.core.network

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.toErrorUnless
import com.rianjaradev.androidtemplate.core.exception.CommonError
import com.rianjaradev.androidtemplate.core.exception.NetworkError
import com.rianjaradev.androidtemplate.core.exception.ServerError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
internal inline fun <V> runCatchingResult(block: () -> V): Result<V, Throwable> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return try {
        Ok(block())
    } catch (e: Throwable) {
        Err(e)
    }
}

suspend fun <T> serviceCallResultWrapper(
    dispatchers: CoroutineDispatcher,
    call: suspend () -> Response<T>
): Result<T, CommonError> = withContext(dispatchers) {
    runCatchingResult {
        call.invoke()
    }.mapError { throwable ->
        NetworkError(message = throwable.message.orEmpty())
    }.toErrorUnless(
        predicate = { response ->
            response.isSuccessful && response.errorBody() == null
        },
        transform = { response ->
            ServerError(
                code = response.code().toString(),
                message = response.errorBody()?.string() ?: ""
            )
        }
    ).map { response ->
        response.body()!!
    }
}