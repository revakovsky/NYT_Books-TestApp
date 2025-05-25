package com.revakovskyi.core.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.revakovskyi.core.presentation.utils.UiText.DynamicString
import com.revakovskyi.core.presentation.utils.UiText.StringResource

/**
 * Represents a UI-friendly text that can either be a dynamic raw string or a string resource with optional formatting arguments.
 *
 * This sealed interface helps unify text rendering in the UI, especially when text may come from either a remote source (dynamic)
 * or a localized resource (string resource). It is especially useful in view models and UI states where direct access to
 * `Context` or `@Composable` functions is not available.
 *
 * ## Types:
 * - [DynamicString]: A raw string value provided at runtime.
 * - [StringResource]: A string resource ID with optional formatting arguments.
 *
 * ## Example usage:
 * ```kotlin
 * val errorText: UiText = UiText.StringResource(R.string.error_message, arrayOf("John"))
 * val titleText: UiText = UiText.DynamicString("Welcome!")
 * ```
 */
sealed interface UiText {

    /**
     * Represents a string that is already formatted and can be used directly.
     *
     * @param value The plain string.
     */
    data class DynamicString(val value: String) : UiText


    /**
     * Represents a string resource ID with optional arguments for formatting.
     *
     * @param id The string resource ID.
     * @param args The arguments to be formatted into the string.
     */
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf(),
    ) : UiText


    /**
     * Resolves the [UiText] to a localized [String] in a Compose environment.
     *
     * @return The resolved string.
     */
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id = id, *args)
        }
    }

    /**
     * Resolves the [UiText] to a localized [String] using a given [Context], useful outside of Compose.
     *
     * @param context The context used to access resources.
     * @return The resolved string.
     */
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }

}
