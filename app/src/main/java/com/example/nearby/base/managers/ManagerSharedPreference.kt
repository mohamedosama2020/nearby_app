package com.example.nearby.base.managers

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by mohamed abd elnaby on 16/April/2019
 */

class ManagerSharedPreference {
    private lateinit var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null
    private var context: Context? = null
//    private val loginDataUser = "LoginUser"

//    fun setLoginDataUser(loginDataUser: String) {
//        editor?.putString(this.loginDataUser, loginDataUser)
//        editor?.commit()
//    }
//
//    fun getLoginDataUser(): LoginResponse? {
//        return try {
//            Gson().fromJson(
//                sharedPreferences.getString(loginDataUser, "") ?: "",
//                LoginResponse::class.java
//            )
//
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            null
//        }
//    }

    companion object {

        private const val ShareName = "SharedPrefs"
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: ManagerSharedPreference

        @SuppressLint("CommitPrefEdits")
        fun initial(context: Context) {
            instance =
                ManagerSharedPreference()
            instance.context = context
            instance.sharedPreferences =
                context.getSharedPreferences(ShareName, Context.MODE_PRIVATE)
            instance.editor = instance.sharedPreferences.edit()

        }


    }

}
