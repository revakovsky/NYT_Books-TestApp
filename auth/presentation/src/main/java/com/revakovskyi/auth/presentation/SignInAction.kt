package com.revakovskyi.auth.presentation

sealed interface SignInAction {

    data object FirstAction : SignInAction

}
