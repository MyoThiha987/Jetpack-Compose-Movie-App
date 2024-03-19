@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.devToolsKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinXSerialization)
    alias(libs.plugins.googleProtobuf)
}

android {
    namespace = "com.myothiha.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    api(project(":domain"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(libs.datastore)
    implementation(libs.google.protobuf)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)
    ksp(libs.room.compiler)
    implementation(libs.bundles.ktor)
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)
    implementation(libs.serialization.kotlinx)
    implementation(libs.bundles.room)
    implementation(libs.bundles.coroutine)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.18.0"
    }
    generateProtoTasks {
        all().onEach { task ->
            task.builtins {
                register("java") { option("lite") }
                //register("kotlin") { option("lite") }
            }
        }
    }
}