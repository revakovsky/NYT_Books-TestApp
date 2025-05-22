package com.revakovskyi.auth.presentation.di

import com.revakovskyi.auth.presentation.SignInViewModel
import com.revakovskyi.auth.presentation.auth_client.AppAuthClient
import com.revakovskyi.auth.presentation.auth_client.AuthClient
import com.revakovskyi.auth.presentation.auth_client.firebase.FirebaseAuthenticator
import com.revakovskyi.auth.presentation.auth_client.firebase.FirebaseAuthenticatorImpl
import com.revakovskyi.auth.presentation.auth_client.google.GoogleAuthClient
import com.revakovskyi.auth.presentation.auth_client.google.GoogleAuthClientImpl
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authPresentationModule = module {

    viewModelOf(::SignInViewModel)

    singleOf(::FirebaseAuthenticatorImpl).bind<FirebaseAuthenticator>()
    singleOf(::GoogleAuthClientImpl).bind<GoogleAuthClient>()
    singleOf(::AppAuthClient).bind<AuthClient>()

}
