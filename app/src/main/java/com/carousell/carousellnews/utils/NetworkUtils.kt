package com.carousell.carousellnews.utils

import android.util.Log
import com.carousell.carousellnews.MyApplication
import com.carousell.carousellnews.R
import com.carousell.carousellnews.data.model.GenericResponse
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * This object class is responsible for identifying the exception.
 * @author: Jagannath Acharya
 */
object NetworkUtils {
    /**
     * This function is responsible for providing us the error message based on exceptions.
     * @param: throwable
     * @return: String
     * @author: Jagannath Acharya
     */
    fun exceptionHandler(throwable: Throwable): String {
        throwable.message?.let { Log.e("error_message", it) }
        return when (throwable) {
            is HttpException -> {
                val response = throwable.response()?.errorBody()?.string()
                when (throwable.response()?.code()) {
                    400 -> MyApplication.INSTANCE.getString(R.string.bad_request_error)
                    401 -> MyApplication.INSTANCE.getString(R.string.unauthorized_error)
                    404 -> MyApplication.INSTANCE.getString(R.string.request_not_found_error)
                    500 -> {
                        if (!response.isNullOrEmpty())
                            GsonUtils.jsonToGson<GenericResponse>(response).message ?: MyApplication.INSTANCE.getString(R.string.something_went_wrong_error)
                        else
                            MyApplication.INSTANCE.getString(R.string.something_went_wrong_error)
                    }
                    else -> MyApplication.INSTANCE.getString(R.string.something_went_wrong_error)
                }
            }
            is ConnectException -> MyApplication.INSTANCE.getString(R.string.no_internet_error)
            is SocketTimeoutException -> MyApplication.INSTANCE.getString(R.string.slow_internet_error)
            is TimeoutException -> MyApplication.INSTANCE.getString(R.string.request_timeout_error)
            is UnknownHostException -> MyApplication.INSTANCE.getString(R.string.no_internet_error)
            else -> MyApplication.INSTANCE.getString(R.string.something_went_wrong_error)
        }
    }
}