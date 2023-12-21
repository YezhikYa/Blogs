pluginManagement {
    repositories {
        google()
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

rootProject.name = "Blogs"
include(":app")
include(":MODEL")
include(":HELPER")
include(":REPOSITORY")
include(":VIEWMODEL")
include(":REPOSITwORY")
