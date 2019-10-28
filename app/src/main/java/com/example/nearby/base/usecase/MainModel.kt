package com.example.nearby.base.usecase


import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import com.example.nearby.base.MainApp
import com.example.nearby.base.vm.CommonStates
import com.example.nearby.network.ApiClient
import io.reactivex.disposables.CompositeDisposable



@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
abstract class MainModel {

    val apiClient = ApiClient.apiClient
    private val disposable: CompositeDisposable = CompositeDisposable()
    fun getDisposable(any: Any, showProgress: Boolean): CompositeDisposable? {
        return if (isInternetConnected()) {
            if (showProgress)
                (any as MutableLiveData<*>).value = CommonStates.LoadingShow
            disposable
        } else {
            (any as MutableLiveData<*>).value = CommonStates.NoInternet
            null
        }
    }

    fun getDisposable(any: Any, showProgress: Boolean, isMore: Boolean): CompositeDisposable? {
        return if (isInternetConnected()) {
            if (showProgress)
                (any as MutableLiveData<*>).value = CommonStates.LoadingShow
            else if (isMore)
                (any as MutableLiveData<*>).value = CommonStates.LoadingShowMore
            disposable
        } else {
            (any as MutableLiveData<*>).value = CommonStates.NoInternet
            null
        }
    }

    private fun isInternetConnected(): Boolean {

        val connectivityManager =
            MainApp.mainApp.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }


    fun clear() {
        disposable.clear()
    }


}
