package com.carousell.carousellnews.utils

import com.carousell.carousellnews.MyApplication
import com.carousell.carousellnews.R
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * This object class is responsible for identifying the exception and the corresponding animation file.
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
        return when (throwable) {
            is HttpException -> {
                when (throwable.response()?.code()) {
                    400 -> MyApplication.INSTANCE.getString(R.string.bad_request_error)
                    401 -> MyApplication.INSTANCE.getString(R.string.unauthorized_error)
                    404 -> MyApplication.INSTANCE.getString(R.string.request_not_found_error)
                    500 -> MyApplication.INSTANCE.getString(R.string.internal_server_error)
                    else -> MyApplication.INSTANCE.getString(R.string.something_went_wrong_error)
                }
            }
            is JsonParseException -> MyApplication.INSTANCE.getString(R.string.bad_data_received_error)
            is ConnectException -> MyApplication.INSTANCE.getString(R.string.no_internet_error)
            is SocketTimeoutException -> MyApplication.INSTANCE.getString(R.string.slow_internet_error)
            is TimeoutException -> MyApplication.INSTANCE.getString(R.string.request_timeout_error)
            is UnknownHostException -> MyApplication.INSTANCE.getString(R.string.no_internet_error)
            else -> MyApplication.INSTANCE.getString(R.string.something_went_wrong_error)
        }
    }

    /**
     * This function is responsible for providing us the lottie animation file.
     * @param: throwable
     * @return: Int
     * @author: Jagannath Acharya
     */
    fun getTheAnimJson(throwable: Throwable): Int {
        return when (throwable) {
            is HttpException -> {
                when (throwable.response()?.code()) {
                    400 -> R.raw.anim_400
                    401 -> R.raw.anim_401
                    404 -> R.raw.anim_404
                    500 -> R.raw.anim_500
                    else -> R.raw.anim_something_went_wrong
                }
            }
            is JsonParseException -> R.raw.anim_bad_data
            is ConnectException -> R.raw.anim_no_internet
            is SocketTimeoutException -> R.raw.anim_slow_internet
            is TimeoutException -> R.raw.anim_request_timeout
            is UnknownHostException -> R.raw.anim_no_internet
            else -> R.raw.anim_something_went_wrong
        }
    }
}