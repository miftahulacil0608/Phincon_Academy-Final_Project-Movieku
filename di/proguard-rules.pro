# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Hilt
-keep class dagger.hilt.** { *; }
-keep @dagger.hilt.* class * { *; }
-keep class javax.inject.** { *; }
-keepattributes Signature, RuntimeVisibleAnnotations

# Room
-keep class androidx.room.** { *; }
-keep @androidx.room.* class * { *; }
-keep interface androidx.room.* { *; }
-keepattributes *Annotation*

# DataStore
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# Retrofit and Gson
-keep class retrofit2.** { *; }
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# Firebase
-keep class com.google.firebase.** { *; }
-keepattributes Signature, RuntimeVisibleAnnotations
-dontwarn com.google.firebase.**
-keep class com.google.android.gms.auth.api.identity.** { *; }

# Core AndroidX Libraries
-keep class androidx.core.** { *; }
-keep class androidx.appcompat.** { *; }
-keep class com.google.android.material.** { *; }

# Testing (Junit and Espresso)
-keep class junit.** { *; }
-dontwarn junit.**
-keep class androidx.test.espresso.** { *; }
-dontwarn androidx.test.espresso.**

# General
-keepattributes *Annotation*
-keepclassmembers class kotlin.Metadata { *; }
-keep class java.lang.Class { *; }

-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile