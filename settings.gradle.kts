@file:Suppress("UnstableApiUsage")

pluginManagement {
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

rootProject.name = "NYT_Books"

include(":app")

include(":auth:data")
include(":auth:domain")
include(":auth:presentation")

include(":core:database")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:presentation:design_system")
include(":core:presentation:shared_ui")

include(":books:data")
include(":books:domain")
include(":books:presentation")
