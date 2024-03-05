package com.myothiha.appbase.exception

import android.content.Context
import com.myothiha.appbase.R
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by Vincent on 11/27/19
 *
 * Modified by MyoThiha
 */
class ExceptionMapperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ExceptionMapper {
    companion object {
        private const val ERROR_CODE_400 = 400
        private const val ERROR_CODE_401 = 401
        private const val ERROR_CODE_422 = 422
        private const val ERROR_CODE_403 = 403
        private const val ERROR_CODE_404 = 404
        private const val ERROR_CODE_500 = 500
    }

    override fun map(item: Throwable): String {
        return when (item) {
            is UnknownHostException -> context.getString(R.string.error_no_internet)
            is SocketTimeoutException -> context.getString(R.string.error_socket_timeout)
            is ConnectException -> context.getString(R.string.error_no_internet)
            is NetworkException -> parseNetworkError(item)
            else -> {
                item.localizedMessage?: context.getString(R.string.error_app)
            }
        }
    }

    private fun parseNetworkError(exception: NetworkException): String {
        when (exception.errorCode) {
            ERROR_CODE_400 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
                ?: context.getString(
                    R.string.error_400
                )
            ERROR_CODE_401 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
                ?: context.getString(
                    R.string.error_401
                )
            ERROR_CODE_422 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
                ?: context.getString(
                    R.string.error_4022
                )
            ERROR_CODE_403 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
                ?: context.getString(
                    R.string.error_403
                )
            ERROR_CODE_404 -> return context.getString(R.string.error_server_404)
            ERROR_CODE_500 -> return context.getString(R.string.error_server_500)
        }

        return context.getString(R.string.error_generic)
    }

    private fun parseMessageFromErrorBody(errorBody: String): String {
        try {
            val errorBodyJson = JSONObject(errorBody)
            return errorBodyJson.getString("errors")
        } catch (exception: Exception) {
        }
        return context.getString(R.string.error_generic)
    }

}



