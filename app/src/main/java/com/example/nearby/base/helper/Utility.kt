package com.example.nearby.base.helper

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.ConnectivityManager
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.nearby.R

object Utility {


    val PermessionCodeLocation = 600
    var locationLiveData = MutableLiveData<Location>()
    const val PermissionCode = 601
    lateinit var alertDialog: AlertDialog

//    fun getDateNow(): String {
//        val pattern = "yyyy-MM-dd"
//        val dateFormat = SimpleDateFormat(pattern, Locale("en"))
//        return dateFormat.format(Date())
//    }
//
//    fun getTimeNow(): String {
//        val pattern = "HH:mm"
//        val dateFormat = SimpleDateFormat(pattern, Locale("en"))
//        return dateFormat.format(Date())
//    }


//    fun disableShiftMode(view: BottomNavigationView) {
//        val menuView = view.getChildAt(0) as BottomNavigationMenuView
//        try {
//            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
//            shiftingMode.isAccessible = true
//            shiftingMode.setBoolean(menuView, false)
//            shiftingMode.isAccessible = false
//            for (i in 0 until menuView.childCount) {
//                val item = menuView.getChildAt(i) as BottomNavigationItemView
//
//                item.setShiftingMode(false)
//                // set once again checked value, so view will be updated
//                item.setChecked(item.da)
//            }
//        } catch (e: NoSuchFieldException) {
//            Log.e("BNVHelper", "Unable to get shift mode field", e)
//        } catch (e: IllegalAccessException) {
//            Log.e("BNVHelper", "Unable to change value of shift mode", e)
//        }
//
//    }

    fun isInterentConnected(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            true
        } else {
            errorDialog(context, "Something Went Wrong")

            false
        }
    }


    @SuppressLint("SetTextI18n")
    fun errorDialog(context: Context, msg: String) {
        val params =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(10, 10, 10, 10)
        try {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(false)
            val tv = TextView(context)
            tv.text = "Something Went Wrong"
            tv.gravity = Gravity.CENTER_HORIZONTAL
            tv.textSize = 16F
            tv.layoutParams = params
            tv.setTextColor(Color.RED)
            builder.setCustomTitle(tv)
            builder.setMessage(msg)
            builder.setNegativeButton(context.getString(R.string.OK)) { _, _ ->
            }
            alertDialog = builder.create()
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            } else {
                alertDialog.show()

            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    @SuppressLint("SetTextI18n")
    fun errorDialog(context: Context, msg: String, action: Dialog.() -> Unit) {

        try {

            val builder = AlertDialog.Builder(context)
            builder.setCancelable(false)
            val tv = TextView(context)
            tv.text = "Error"
            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            params.setMargins(10, 10, 10, 10)
            tv.gravity = Gravity.CENTER_HORIZONTAL
            tv.textSize = 16F
            tv.layoutParams = params
            tv.setTextColor(Color.RED)
            builder.setCustomTitle(tv)
            builder.setMessage(msg)

            builder.setNegativeButton(context.getString(R.string.OK)) { _, _ ->
                action(alertDialog)
            }
            alertDialog = builder.create()
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            } else {
                alertDialog.show()

            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    fun errorDialog(
        context: Context,
        msg: String,
        dialogInterface: DialogInterface,
        iscanceable: Boolean
    ) {

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(context.getString(R.string.error))
        builder.setMessage(msg)
        builder.setNegativeButton(context.getString(R.string.OK)) { _, _ -> dialogInterface.cancel() }
        alertDialog = builder.create()
        alertDialog.setCancelable(iscanceable)
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        } else {
            alertDialog.show()

        }
    }

    fun errorDialog(context: Context, msg: String, iscanceable: Boolean, action: () -> Unit) {

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(context.getString(R.string.error))
        builder.setMessage(msg)

        builder.setNegativeButton(context.getString(R.string.OK)) { _, _ -> action() }
        alertDialog = builder.create()
        alertDialog.setCancelable(iscanceable)
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        } else {
            alertDialog.show()

        }
    }


    fun infoDailog(
        context: Context,
        msg: String,
        dialogInterface: DialogInterface?,
        isClickable: Boolean
    ) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.info))
        builder.setMessage(msg)
        builder.setNegativeButton(context.getString(R.string.OK)) { _, _ -> dialogInterface?.cancel() }
        alertDialog = builder.create()
        alertDialog.setCancelable(isClickable)
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        } else {
            alertDialog.show()

        }
    }

    fun infoDailog(context: Context, msg: String, isClickable: Boolean, action: () -> Unit) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Info")
        builder.setMessage(msg)
        builder.setNegativeButton("OK") { _, _ -> action() }
        alertDialog = builder.create()
        alertDialog.setCancelable(isClickable)
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        } else {
            alertDialog.show()

        }
    }

    fun infoDailog(context: Context, msg: String) {

        val builder = AlertDialog.Builder(context)
        val tv = TextView(context)
        tv.text = context.getString(R.string.info)
        val params =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(10, 10, 10, 10)
        tv.gravity = Gravity.CENTER_HORIZONTAL
        tv.textSize = 16F
        tv.layoutParams = params
        builder.setCustomTitle(tv)
        builder.setMessage(msg)

        builder.setNegativeButton(context.getString(R.string.OK)) { dialog, which -> dialog.dismiss() }
        alertDialog = builder.create()
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        } else {
            alertDialog.show()

        }
    }

     fun checkPermission(permission: String, activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

     fun requestPermission(permission: String, activity: Activity) {
        if (permission == Manifest.permission.ACCESS_COARSE_LOCATION || permission == Manifest.permission.ACCESS_FINE_LOCATION) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                PermessionCodeLocation
            )
        } else
            ActivityCompat.requestPermissions(activity, arrayOf(permission), Utility.PermissionCode)
    }
}


