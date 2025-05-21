plugins {
    alias(libs.plugins.nytbooks.android.library)
}

android {
    namespace = "com.revakovskyi.auth.data"
}

dependencies {

    // Modules
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    //Koin
    implementation(project.libs.bundles.koin)

    // Auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.auth)

}