import java.util.Properties

val localPropertiesFile = File(rootDir, "local.properties")
if (!localPropertiesFile.exists()) {
    val sdkDirFromEnv = sequenceOf("ANDROID_HOME", "ANDROID_SDK_ROOT")
        .mapNotNull { System.getenv(it)?.takeIf(String::isNotBlank) }
        .firstOrNull()

    if (sdkDirFromEnv != null) {
        val properties = Properties().apply {
            setProperty("sdk.dir", sdkDirFromEnv)
        }
        localPropertiesFile.outputStream().use { properties.store(it, null) }
    }
}

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
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "v2rayNG"
include(":app")
