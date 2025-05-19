plugins {
    alias(libs.plugins.nytbooks.android.library)
}

android {
    namespace = "com.revakovskyi.core.data"
}

dependencies {

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.database)

    //Koin
    implementation(project.libs.bundles.koin)

}