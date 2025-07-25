plugins {
    alias(libs.plugins.nytbooks.android.feature.ui)
}

android {
    namespace = "com.revakovskyi.books.presentation"
}

dependencies {

    // Modules
    implementation(projects.books.domain)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.theme)
    implementation(projects.core.presentation.utils)
    implementation(projects.core.presentation.designSystem)

    // Koin
    implementation(project.libs.bundles.koin)

    // Coil
    implementation(project.libs.coil.compose)

    // Android Custom Tabs
    implementation(project.libs.androidx.browser)

    // Timber
    implementation(project.libs.timber)

}