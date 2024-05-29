package com.tsp.nicegetup

import android.app.Application
import android.content.Context

class NiceApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer(this)

    }







}

class AppContainer(val context: Context) {

    val myLocationManger by lazy {
        MyLocationManger(context)
    }


}