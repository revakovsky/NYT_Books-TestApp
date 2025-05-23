package com.revakovskyi.nytbooks.utils

import timber.log.Timber

object DefaultLogger {

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


    private class TaggedDebugTree(private val defaultTag: String?) : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            val usedTag = defaultTag ?: tag
            super.log(priority, usedTag, message, t)
        }
    }

}
