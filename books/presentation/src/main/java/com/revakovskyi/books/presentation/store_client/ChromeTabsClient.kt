package com.revakovskyi.books.presentation.store_client

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import timber.log.Timber

object ChromeTabsClient {

    fun openStore(
        context: Context,
        url: String,
        onError: () -> Unit,
    ) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(false)
            .setUrlBarHidingEnabled(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)
            .build()

        try {
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } catch (e: Exception) {
            Timber.e(e, "Error opening Chrome Tab")
            onError()
        }
    }

}
