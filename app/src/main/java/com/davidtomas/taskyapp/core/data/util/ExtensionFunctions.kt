package com.davidtomas.taskyapp.core.data.util

import android.util.Log
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import kotlinx.serialization.SerializationException
import java.io.IOException

suspend inline fun <reified D> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): Result<D, DataError.Network> =
    try {
        val response = request { block() }
        Result.Success(response.body())
    } catch (e: ServerResponseException) {
        Log.d("Network Error", e.toString())
        Result.Error(DataError.Network.SERVER_ERROR)
    } catch (e: ClientRequestException) {
        Log.d("Network Error", e.toString())
        when (e.response.status.value) {
            401 -> Result.Error(DataError.Network.UNAUTHORIZED)
            409 -> Result.Error(DataError.Network.BAD_REQUEST)
            else -> Result.Error(DataError.Network.UNKNOWN)
        }
    } catch (e: IOException) {
        Log.d("Network Error", e.toString())
        Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        Log.d("Network Error", e.toString())
        Result.Error(DataError.Network.SERIALIZATION)
    }
