@file:Suppress("SpellCheckingInspection")

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
    id("com.jfrog.bintray")
}

android {
    setDefaults()
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
    metadataPublication(project)
    androidWithPublication(project)
    sourceSets {
        getByName("commonMain").dependencies {
            api(kotlin("stdlib-common"))
            api(Libs.kotlinX.coroutines.coreCommon)
        }
        getByName("androidMain").dependencies {
            api(Libs.kotlinX.coroutines.android)
            api(Libs.androidX.annotation)
            implementation(Libs.splitties.appctx)
            implementation(Libs.splitties.bitflags)
            implementation(Libs.splitties.checkedlazy)
            implementation(Libs.splitties.lifecycleCoroutines)
            implementation(Libs.splitties.mainthread)
        }
        getByName("iosMain").dependencies {
            api(Libs.kotlinX.coroutines.coreNative)
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }

    bintray {
        setupPublicationsUpload(project, publishing, skipMetadataPublication = true)
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