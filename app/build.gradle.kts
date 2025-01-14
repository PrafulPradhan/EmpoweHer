import com.chaquo.python.pythonVersionInfo

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.chaquo.python")
}

android {
    namespace = "com.example.empoweher"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.empoweher"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters.add("arm64-v8a")
            abiFilters.add("x86_64")
        }

        chaquopy {
            defaultConfig { }
            productFlavors { }
            sourceSets { }
        }
////
        chaquopy {
            defaultConfig {
                version = "3.12"
                buildPython("C:/Users/ruksa/AppData/Local/Programs/Python/Python312/python.exe")
                pip {
                    install("tweepy")
                    install("SpeechRecognition")
                    install("playsound")
                    install("sounddevice")
                    install("numpy")
                }
            }
//            sourceSets {
//                main {
//                    python.srcDir("src/main/python")
//                }
//            }
        }
//
//

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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }

    chaquopy {
        sourceSets {
            getByName("main") {
                srcDir("src/main/python")
            }
        }
    }

    buildFeatures {
        compose = true
        viewBinding=true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("com.github.ZEGOCLOUD:zego_uikit_prebuilt_video_conference_android:+")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.room:room-common:2.6.1")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.privacysandbox.tools:tools-core:1.0.0-alpha10")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    //FireBase
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation ("io.coil-kt:coil-compose:2.5.0")

    //Room
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    
    implementation("androidx.room:room-ktx:2.6.1")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.49")
    kapt ("com.google.dagger:hilt-android-compiler:2.49")
    kapt ("androidx.hilt:hilt-compiler:1.1.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Location
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")

    //WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    //Shared Pref
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //Exo player
    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")

    //Image Slider
    implementation ("com.google.accompanist:accompanist-pager:0.22.0-rc")

    //Backpress
    implementation("androidx.test.espresso:espresso-core:3.5.1")

    //Glow
    implementation("io.coil-kt:coil-compose:2.5.0")

    //Payment
    implementation("com.razorpay:checkout:1.6.33")

    //Volley
    implementation("com.android.volley:volley:1.2.1")
}