package com.example.currentrack.data.repositories.connectivity

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject


class ConnectivityDelegateImpl
@Inject constructor(
    private val connectivityManager: ConnectivityManager,
) : ConnectivityDelegate {
    /**
     * The function checks if the device is connected to the internet by checking the network
     * capabilities.
     *
     * @return a boolean value.
     */
    override fun isConnected(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
    }
}