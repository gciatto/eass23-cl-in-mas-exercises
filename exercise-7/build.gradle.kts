plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform(libs.kotlin.bom))
    // Use the Kotlin JDK 8 standard library.
    implementation(libs.kotlin.stdlib.jvm)

    implementation(libs.tuprolog.theory)
    implementation(libs.tuprolog.parser.theory)

    // Use the Kotlin test library.
    testImplementation(libs.kotlin.test)
    // Use the Kotlin JUnit integration.
    testImplementation(libs.kotlin.test.junit)
}
