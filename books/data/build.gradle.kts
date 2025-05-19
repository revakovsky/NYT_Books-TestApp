plugins {
    alias(libs.plugins.nytbooks.android.library)
}

android {
    namespace = "com.revakovskyi.books.data"
}

dependencies {

    // Modules
    implementation(projects.books.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    //Koin
    implementation(project.libs.bundles.koin)

}