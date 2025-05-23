plugins {
    alias(libs.plugins.nytbooks.android.library)
    alias(libs.plugins.nytbooks.jvm.ktor)
}

android {
    namespace = "com.revakovskyi.core.network"
}

dependencies {

    // Modules
    implementation(projects.core.domain)

    //Koin
    implementation(project.libs.bundles.koin)

    //Timber
    implementation(project.libs.timber)

}