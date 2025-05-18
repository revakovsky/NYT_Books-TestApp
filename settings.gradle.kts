@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")


rootProject.name = "NYT_Books"

include(":app")

include(":auth:data")
include(":auth:domain")
include(":auth:presentation")

include(":core:database")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:presentation:theme")
include(":core:presentation:design_system")
include(":core:presentation:utils")

include(":books:data")
include(":books:domain")
include(":books:presentation")
