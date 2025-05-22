plugins {
    alias(libs.plugins.nytbooks.android.application.compose)
    alias(libs.plugins.nytbooks.jvm.ktor)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
}

android {
    namespace = libs.versions.appId.get()

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    // Modules Core
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.theme)
    implementation(projects.core.presentation.designSystem)
    implementation(projects.core.presentation.utils)

    // Modules Auth
    implementation(projects.auth.presentation)

    // Modules Books
    implementation(projects.books.data)
    implementation(projects.books.domain)
    implementation(projects.books.presentation)


    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Koin
    implementation(libs.bundles.koin)
    ksp(libs.koin.ksp.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))

}
