plugins {
    alias(libs.plugins.nytbooks.android.library)
    alias(libs.plugins.nytbooks.android.room)
}

android {
    namespace = "com.revakovskyi.core.database"
}

dependencies {

    // Modules
    implementation(projects.core.domain)

    //Koin
    implementation(project.libs.bundles.koin)

}