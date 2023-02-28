package com.carousell.carousellnews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carousell.carousellnews.data.error.ErrorCallback
import com.carousell.carousellnews.utils.NetworkUtils
import kotlinx.coroutines.*

abstract class ViewModelBase(application: Application): AndroidViewModel(application) {
    init {
        onCreate()
    }
    val error = MutableLiveData<ErrorCallback>()
    private val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        error.postValue(ErrorCallback(true, NetworkUtils.exceptionHandler(throwable)))
    }

    fun launchCoroutineScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler, CoroutineStart.DEFAULT, block)
    }

    abstract fun onCreate()
}