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

rootProject.name = "Coroutine"
include(":app")
include(":common")
include(":media_player")
include(":feature:search:ui")
include(":feature:search:domain")
include(":feature:search:data")
include(":features")
include(":feature:searchf")
include(":feature1:search1:ui")
include(":feature1:search1:mylibrary")
include(":feature1:search1:data")
include(":feature1:search1:domain")
