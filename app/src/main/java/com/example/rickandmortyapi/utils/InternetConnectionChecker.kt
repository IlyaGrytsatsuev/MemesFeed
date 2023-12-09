package com.example.rickandmortyapi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class InternetConnectionChecker @Inject constructor(val context: Context) {
    fun hasInternetConnection():Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?:
            return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?:
            return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                    return true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                    return true
                else ->
                    return false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?:
                return false
            @Suppress("DEPRECATION")
            return true
        }
    }
}