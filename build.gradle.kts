// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    val agp_version by extra("8.2.0")
    val agp_version1 by extra("8.2.0")
    val agp_version2 by extra("8.2.0")
    val agp_version3 by extra("8.2.0")
    val agp_version4 by extra("8.3.0")
    val agp_version5 by extra("8.3.0")
    dependencies {
        classpath ("com.google.gms:google-services:4.4.0")
    }
    repositories {
        maven {
            url=uri("https://chaquo.com/maven")
        }
    }

}


plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
    //Chaquopy
    id ("com.chaquo.python") version "15.0.1" apply false


}

