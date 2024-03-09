import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.serialization)
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
    kotlinOptions {
        jvmTarget = BuildVersion.Environment.jvmTarget
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.bundles.testing.android)
    debugImplementation(libs.bundles.compose.debug)
    // Splash
    implementation(libs.splash.api)
    // Navigation
    implementation(libs.compose.navigation)
    // Koin
    implementation(libs.bundles.koin)
    // Ktor Client
    implementation(libs.bundles.ktor)
    // Data Store
    implementation(libs.bundles.datastore)
}
