package com.carousell.carousellnews

import android.app.Application

/**
 * This class is responsible for providing us context of the app through the project.
 * @author: Jagannath Acharya
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: MyApplication
            private set
    }
}