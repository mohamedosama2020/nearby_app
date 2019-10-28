@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.nearby.base.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nearby.entities.ApiErrores
import com.google.gson.Gson
import retrofit2.HttpException


fun Activity.handleApiError(e: Throwable) {
    e.handleApiError { _, message ->
        Utility.errorDialog(this, message)
    }
}


inline fun Throwable.handleApiError(action: (Int, String) -> Unit) {
    if (this is HttpException) {
        if (code() in 400..600) {
            try {
                val dataSting = response()?.errorBody()?.string()
                val data = Gson().fromJson(dataSting, ApiErrores::class.java)
                val message = data?.msg ?: ""
                action(code(), message)
            } catch (e: Exception) {
                e.printStackTrace()

                action(code(), "")
            }
        }

    } else {
        val errorMessage: String = localizedMessage
        Log.d("Not_HTTP", errorMessage)
    }

}

fun Activity.requestPermission(permission: String): Boolean {
    return if (checkPermission(permission, this)) {
        true
    } else {
        requestPermission(permission, this)
        false
    }
}

private fun checkPermission(permission: String, activity: Activity): Boolean {
    return ContextCompat.checkSelfPermission(
        activity,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

private fun requestPermission(permission: String, activity: Activity) {
    if (permission == Manifest.permission.ACCESS_COARSE_LOCATION || permission == Manifest.permission.ACCESS_FINE_LOCATION) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            Utility.PermessionCodeLocation
        )
    } else
        ActivityCompat.requestPermissions(activity, arrayOf(permission), Utility.PermissionCode)


}