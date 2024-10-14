plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}


val pluginName = "Geolocation"
val pluginPackageName = "de.wolfbeargames.geolocationplugin"

android {
    namespace = pluginPackageName
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24

        manifestPlaceholders["godotPluginName"] = pluginName
        manifestPlaceholders["godotPluginPackageName"] = pluginPackageName
        buildConfigField("String", "GODOT_PLUGIN_NAME", "\"${pluginName}\"")
        setProperty("archivesBaseName", pluginName)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("org.godotengine:godot:4.3.0.stable")
    implementation(libs.google.gms)
}

val copyDescriptorsToOutput by tasks.registering(Copy::class) {
    description = "Copies the Godot plugin descriptos to output directory."

    from("godot_descriptors")
    into("${project.buildDir}/outputs/aar")
}

tasks.named("build").configure {
    finalizedBy(copyDescriptorsToOutput)
}