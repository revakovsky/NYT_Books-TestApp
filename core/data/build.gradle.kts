plugins {
    alias(libs.plugins.nytbooks.android.library)
}

android {
    namespace = "com.revakovskyi.core.data"
}

dependencies {

    // Modules
    implementation(projects.core.domain)

    //Koin
    implementation(project.libs.bundles.koin)

}