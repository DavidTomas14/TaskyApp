import org.gradle.api.JavaVersion

object BuildVersion {
    object Environment {
        private const val majorVersion = 1
        private const val minorVersion = 0
        private const val bugfixVersion = 0

        const val minSdkVersion = 26
        const val compileSdkVersion = 34
        const val targetSdkVersion = 34
        const val applicationId = "com.davidtomas.taskyapp"
        const val appVersionCode = majorVersion * 1000 + minorVersion * 100 + bugfixVersion
        const val appVersionName = "${majorVersion}.${minorVersion}.$bugfixVersion"
        val javaVersion = JavaVersion.VERSION_18
        const val jvmTarget = 18
    }

    object TestEnvironment {
        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //const val instrumentationRunner = "com.davidtomas.taskyapp.InstrumentationTestRunner"
        const val instrumentationRunnerArgs = "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }
}
