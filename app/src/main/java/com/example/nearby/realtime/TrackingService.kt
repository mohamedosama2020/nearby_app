package com.example.nearby.realtime

import android.Manifest
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.example.nearby.base.helper.Utility
import com.google.android.gms.location.*

class TrackingService : Service() {

    lateinit var client: FusedLocationProviderClient
    lateinit var locationCallBack: LocationCallback

    private var stopReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            //Unregister the BroadcastReceiver when the notification is tapped//
            unregisterReceiver(this)

            //Stop the Service//
            stopSelf()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationCallBack = object: LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                val location = p0?.lastLocation
                if (location != null) {
                    Utility.locationLiveData.value = location
                }
            }
        }
        requestLocationUpdates()
    }


    private fun requestLocationUpdates() {
        val request = LocationRequest()

        //Specify how often your app should request the device’s location//

        request.interval = (6 * 1000).toLong()

        //Get the most accurate location data available//

        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        client = LocationServices.getFusedLocationProviderClient(this)
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        //If the app currently has access to the location permission...//

        if (permission == PackageManager.PERMISSION_GRANTED) {

            //...then request location updates//

            client.requestLocationUpdates(request, locationCallBack, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.stopSelf()
        this.stopService(Intent(this,TrackingService::class.java))
        stopForeground(true)
        client.removeLocationUpdates(locationCallBack)
    }
}