package com.gravity.testapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

object Utils {
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.activeNetwork != null &&
                    with(cm.getNetworkCapabilities(cm.activeNetwork!!)) {
                        this?.hasTransport(TRANSPORT_WIFI) == true ||
                                this?.hasTransport(TRANSPORT_CELLULAR) == true ||
                                this?.hasTransport(TRANSPORT_VPN) == true
                    }
        } else
            cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun Fragment.addBackPress(onBackPressedCallback: OnBackPressedCallback.() -> Unit) =
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            true,
            onBackPressedCallback
        )
}