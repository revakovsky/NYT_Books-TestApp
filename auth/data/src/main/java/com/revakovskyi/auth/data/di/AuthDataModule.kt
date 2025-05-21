package com.revakovskyi.auth.data.di

import com.revakovskyi.auth.data.AppAuthClient
import com.revakovskyi.auth.data.firebase.FirebaseAuthenticator
import com.revakovskyi.auth.data.firebase.FirebaseAuthenticatorImpl
import com.revakovskyi.auth.data.google.GoogleAuthClient
import com.revakovskyi.auth.data.google.GoogleAuthClientImpl
import com.revakovskyi.auth.data.google.GoogleCredentialManager
import com.revakovskyi.auth.data.google.GoogleCredentialManagerImpl
import com.revakovskyi.auth.domain.AuthClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {

    singleOf(::GoogleCredentialManagerImpl).bind<GoogleCredentialManager>()
    singleOf(::FirebaseAuthenticatorImpl).bind<FirebaseAuthenticator>()
    singleOf(::GoogleAuthClientImpl).bind<GoogleAuthClient>()
    singleOf(::AppAuthClient).bind<AuthClient>()

}
