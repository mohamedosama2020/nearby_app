package com.example.nearby.base.customviews

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.nearby.R


class CustomProgressPar(context: Context) : AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_progressbar)
        window.takeIf { it != null }?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

}