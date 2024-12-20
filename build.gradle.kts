// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    id("land.sungbin.dependency.graph.plugin") version "1.1.0"
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
}



dependencyGraphConfig{
    projectName = "Flixt"
    outputFormat = OutputFormat.PNG
    dependencyBuilder {
        with(it){
            when(this.path){
                ":app"->DependencyInfo("#ABCF99")
                ":domain"->DependencyInfo("#FFDA95")
                ":data"->DependencyInfo("#B3C7E7")
                else -> null
            }
        }
    }
}

