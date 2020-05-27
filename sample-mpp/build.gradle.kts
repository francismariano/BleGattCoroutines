@file:Suppress("SpellCheckingInspection")

plugins {
    id("com.android.application")
    kotlin("multiplatform")
}

android {
    compileSdkVersion(ProjectVersions.androidSdk)
    buildToolsVersion(ProjectVersions.androidBuildTools)
    defaultConfig {
        applicationId = "com.beepiz.blegattcoroutines.sample"
        minSdkVersion(18)
        targetSdkVersion(ProjectVersions.androidSdk)
        versionCode = 1
        versionName = ProjectVersions.thisLibrary
        resConfigs("en")
        proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
        proguardFile("proguard-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        pickFirst("META-INF/kotlinx-coroutines-core.kotlin_module")
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storePassword = "android"
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    sourceSets.getByName("main") {
        java.srcDir("src/androidMain/kotlin")
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDir("src/androidMain/res")
    }
}

kotlin {

    iosArm32("ios") {    // device
        binaries {
            framework()
        }
    }
//    iosX64("ios") {     // simulator
//        binaries {
//            framework()
//        }
//    }

    android()
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
                useExperimentalAnnotation("splitties.experimental.ExperimentalSplittiesApi")
                useExperimentalAnnotation("splitties.lifecycle.coroutines.PotentialFutureAndroidXLifecycleKtxApi")
                useExperimentalAnnotation("com.beepiz.bluetooth.gattcoroutines.ExperimentalBleGattCoroutinesCoroutinesApi")
            }
        }
        getByName("commonMain").dependencies {
            api(kotlin("stdlib-common"))
            api(project(":core"))
        }
        getByName("androidMain").dependencies {
            with(Libs) {
                arrayOf(
                        kotlin.stdlibJdk7,
                        androidX.coreKtx,
                        androidX.constraintLayout,
                        timber,
                        kotlinX.coroutines.android,
                        splitties.pack.androidMdcWithViewsDsl,
                        splitties.checkedlazy,
                        splitties.archLifecycle
                )
            }.forEach { api(it) }
            api(project(":genericaccess"))
        }
        getByName("iosMain").dependencies {
            api(Libs.kotlinX.coroutines.coreNative)
        }
    }
}

tasks.register("copyFramework") {
    val buildType = project.findProperty("kotlin.build.type") as? String ?: "DEBUG"
    val target = project.findProperty("kotlin.target") ?: "ios"
//    dependsOn (kotlin.targets."$target".binaries.getFramework(buildType).linkTask)
    dependsOn("kotlin.targets.$target.binaries.getFramework(buildType).linkTask")

    doLast {
//        val srcFile = kotlin.targets."$target".binaries.getFramework(buildType).outputFile
        val srcFile = (kotlin.targets["$target"] as org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget).binaries.getFramework(buildType).outputFile
        val targetDir = project.property("configuration.build.dir")!!
        copy {
            from(srcFile.parent)
            into(targetDir)
            include("app.framework/**")
            include("app.framework.dSYM")
        }
    }
}
