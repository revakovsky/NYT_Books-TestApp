plugins {
    alias(libs.plugins.nytbooks.android.feature.ui)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.revakovskyi.auth.presentation"
}

dependencies {

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.presentation.theme)
    implementation(projects.core.presentation.utils)

    // Koin
    implementation(libs.bundles.koin)
    ksp(libs.koin.ksp.compiler)

    // Auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.auth)

    //Timber
    implementation(project.libs.timber)

}