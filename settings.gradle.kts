plugins {
    id("com.gradle.enterprise") version "3.13.4"
}

dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

rootProject.name = "eass23-cl-in-mas-exercises"

val n = 10

for (i in 1 .. n) {
    include("exercise-$i")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishOnFailure()
    }
}
