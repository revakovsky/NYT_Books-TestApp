package com.revakovskyi.core.data.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import com.revakovskyi.core.domain.connectivity.InternetStatus
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

internal class InternetConnectivityObserver(context: Context) : ConnectivityObserver {

    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override val internetStatus: Flow<InternetStatus>
        get() = callbackFlow {
            sendInitialNetworkStatus()

            val callback = createNetworkCallback()
            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }.distinctUntilChanged()


    private fun ProducerScope<InternetStatus>.sendInitialNetworkStatus() {
        val network = connectivityManager.activeNetwork
        val hasConnection = connectivityManager
            .getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        trySend(
            if (hasConnection) InternetStatus.AVAILABLE
            else InternetStatus.UNAVAILABLE
        )
    }


    private fun ProducerScope<InternetStatus>.createNetworkCallback(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(InternetStatus.AVAILABLE)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                trySend(InternetStatus.LOSING)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(InternetStatus.LOST)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(InternetStatus.UNAVAILABLE)
            }
        }

    }

}
