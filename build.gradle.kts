plugins {
    id("com.github.ben-manes.versions").version("0.25.0")
}

buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.kotlinGradlePlugin)
        classpath(Libs.navigationGradlePlugin)
        classpath(Libs.hiltGradlePlugin)
        "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task<Delete>("clean"){
    delete(rootProject.buildDir)
}