plugins {
    alias(libs.plugins.nytbooks.android.library.compose)
}

android {
    namespace = "com.revakovskyi.core.presentation.utils"
}

dependencies {

    // Modules
    implementation(projects.core.domain)

}