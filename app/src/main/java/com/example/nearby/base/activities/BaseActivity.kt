package com.example.nearby.base.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nearby.base.customviews.CustomProgressPar

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private lateinit var progressBar: CustomProgressPar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = CustomProgressPar(this)

    }


    fun showLoading() {
        progressBar.show()
    }

    fun hideLoading() {
        progressBar.hide()
    }

    private fun cancelLoading() {
        progressBar.dismiss()
        progressBar.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelLoading()
    }

}