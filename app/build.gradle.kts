/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.6/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.jvm)

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

val lwjglVersion = "3.3.3"
val lwjglNatives = "natives-macos-arm64"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    maven { url = uri("https://repo.lwjgl.org/release") }

}

dependencies {
    // Use the Kotlin JUnit 5 integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Use the JUnit 5 integration.
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)

    // LWJL Dependencies:
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

	implementation("org.lwjgl", "lwjgl")
	implementation("org.lwjgl", "lwjgl-assimp")
	implementation("org.lwjgl", "lwjgl-glfw")
	implementation("org.lwjgl", "lwjgl-nanovg")
	implementation("org.lwjgl", "lwjgl-nuklear")
	implementation("org.lwjgl", "lwjgl-openal")
	implementation("org.lwjgl", "lwjgl-opengl")
	implementation("org.lwjgl", "lwjgl-stb")
	implementation("org.lwjgl", "lwjgl-vma")
	implementation("org.lwjgl", "lwjgl-vulkan")
	implementation("org.lwjgl", "lwjgl-xxhash")
	implementation("org.lwjgl", "lwjgl-yoga")
	implementation("org.lwjgl", "lwjgl-zstd")
	runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-nanovg", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-nuklear", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-vma", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-vulkan", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-xxhash", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-yoga", classifier = lwjglNatives)
	runtimeOnly("org.lwjgl", "lwjgl-zstd", classifier = lwjglNatives)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    // Define the main class for the application.
    mainClass = "com.blinktek.AppKt"
    // applicationDefaultJvmArgs = listOf("")
}
tasks.named<JavaExec>("run") {
    // Configure the standard input to accept input from the console
    standardInput = System.`in`
    
}

tasks.withType<JavaExec> {
    jvmArgs("-XstartOnFirstThread")
    jvmArgs("-Xmx4G")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.test {
    // Configure test logging options
    testLogging {
        // Show standard out and standard error of the test execution
        showStandardStreams = true

        // Set events to display in the console output
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
        )
    }
}
