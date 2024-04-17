import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.realm)
    alias(libs.plugins.junit.get5())
}

android {
    namespace = BuildVersion.Environment.applicationId
    compileSdk = BuildVersion.Environment.compileSdkVersion

    defaultConfig {
        applicationId = BuildVersion.Environment.applicationId
        minSdk = BuildVersion.Environment.minSdkVersion
        targetSdk = BuildVersion.Environment.targetSdkVersion
        versionCode = BuildVersion.Environment.appVersionCode
        versionName = BuildVersion.Environment.appVersionName

        testInstrumentationRunner = BuildVersion.TestEnvironment.instrumentationRunner

        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = BuildVersion.Environment.javaVersion
        targetCompatibility = BuildVersion.Environment.javaVersion
    }
    kotlin {
        jvmToolchain(BuildVersion.Environment.jvmTarget)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/INDEX.LIST"
        }
    }
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    disabledRules.set(listOf("import-ordering", "final-newline"))
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.SARIF)
    }
    filter {
        include("**/*.kt")
        exclude("**/build/**")
    }
}

dependencies {

    implementation(libs.bundles.androidx)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    // Splash
    implementation(libs.splash.api)
    // Navigation
    implementation(libs.compose.navigation)
    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    implementation(libs.bundles.koin.test)
    // Ktor Client
    implementation(libs.bundles.ktor)
    // Data Store
    implementation(libs.bundles.datastore)
    // Realm
    implementation(libs.bundles.realm)
    // Testing
    testImplementation(libs.bundles.testing)
    testRuntimeOnly(libs.junit.get5().engine)
    // Android testing
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.bundles.compose.debug)
    // Constraint Layout
    implementation(libs.androidx.constraintlayout.compose)
    // Coil
    implementation(libs.coil.compose)
    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)
}
