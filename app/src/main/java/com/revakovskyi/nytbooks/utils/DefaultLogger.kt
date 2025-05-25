package com.revakovskyi.nytbooks.utils

import timber.log.Timber

/**
 * Provides a centralized logger setup using Timber with optional default tag support.
 *
 * This object is designed to initialize Timber once during application startup
 * and optionally apply a consistent tag to all debug logs.
 *
 * Usage:
 * ```
 * DefaultLogger.init(
 *     isDebug = BuildConfig.DEBUG,
 *     defaultTag = "MyAppTag"
 * )
 * ```
 */
object DefaultLogger {

    /**
     * Initializes Timber logging.
     *
     * @param isDebug Whether the build is a debug build. If true, enables debug logging via [TaggedDebugTree].
     * @param defaultTag Optional default tag to be used for all log statements if none is explicitly provided.
     */
    fun init(
        isDebug: Boolean,
        defaultTag: String? = null,
    ) {
        if (isDebug) {
            Timber.plant(TaggedDebugTree(defaultTag))
        } else {
            // Add any other libraries for logging in production code
        }
    }


    /**
     * A custom Timber tree that allows applying a default tag if none is specified.
     *
     * @param defaultTag The tag to be used for all log messages if the caller doesn't provide one.
     */
    private class TaggedDebugTree(private val defaultTag: String?) : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            val usedTag = defaultTag ?: tag
            super.log(priority, usedTag, message, t)
        }
    }

}
