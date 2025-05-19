plugins {
    alias(libs.plugins.nytbooks.android.feature.ui)
}

android {
    namespace = "com.revakovskyi.auth.presentation"
}

dependencies {

    // Modules
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.theme)
    implementation(projects.core.presentation.utils)

    //Koin
    implementation(project.libs.bundles.koin)

}