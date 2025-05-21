package com.revakovskyi.auth.presentation

import android.app.Activity

sealed interface SignInAction {

    data class SignInWithGoogle(val activity: Activity) : SignInAction

}
