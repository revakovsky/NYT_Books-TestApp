plugins {
    alias(libs.plugins.nytbooks.android.library.compose)
}

android {
    namespace = "com.revakovskyi.core.presentation.design_system"
}

dependencies {

    // Modules
    implementation(projects.core.presentation.theme)
    implementation(projects.core.presentation.utils)

}