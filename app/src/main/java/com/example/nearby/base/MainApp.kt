package com.example.nearby.base

import android.app.Application
import android.os.Handler
import com.example.nearby.base.managers.ManagerSharedPreference
import com.example.nearby.network.ApiClient
import com.google.gson.Gson

class MainApp : Application() {
    private lateinit var gson: Gson
    private var handler: Handler? = null


    override fun onCreate() {
        super.onCreate()
        initializeClass()
        mainApp = this
        handler = Handler()
        gson = Gson()

    }

    private fun initializeClass() {
        ManagerSharedPreference.initial(this)
        ApiClient.initApiClient()

    }


//    fun MSG(msg: String) {
//        handler!!.post {
//            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
//        }
//    }
//
//    fun MSG(MSG: String, time: Int) {
//        handler!!.post { Toast.makeText(this@MainApp, MSG, time).show() }
//    }

    companion object {
        lateinit var mainApp: MainApp
    }


}

