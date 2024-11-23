# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
# Keep Dagger-Hilt related annotations
-keep class dagger.hilt.** { *; }
-dontwarn dagger.hilt.**
-keep class javax.inject.** { *; }
-keep @interface dagger.hilt.**
-keep @interface javax.inject.**
-keep @dagger.Module class *
-keep @dagger.hilt.InstallIn class *
-keep @dagger.hilt.components.SingletonComponent class *

# Keep Hilt generated code
-keep class *Injector {
    *;
}
-keep class **_Factory {
    *;
}
-keep class **_MembersInjector {
    *;
}
-keep class **_ComponentTreeDeps {
    *;
}
-keepattributes Signature, RuntimeVisibleAnnotations

# Keep Retrofit interfaces
-keep interface retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep OkHttp classes
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keepclassmembers class okhttp3.** { *; }

-keep class androidx.room.** { *; }
-keep @androidx.room.** class * { *; }
-dontwarn androidx.room.**

# Keep Firebase SDK classes
-dontwarn com.google.firebase.**
-keep class com.google.firebase.** { *; }
-keepattributes *Annotation*

# Keep Gson serialized/deserialized classes
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*

-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}



# Jaga agar Parcelable tetap utuh
-keepclassmembers class ** implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Parcelable Classes
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

# Keep methods called by reflection in native code
-keepclassmembers class * {
    native <methods>;
}

-repackageclasses 'com.example.obfuscated.data'

# Keep all repository implementations
-keep class com.example.data.repositoryImpl.** { *; }

# Keep all local and remote data sources
-keep class com.example.data.source.local.** { *; }
-keep class com.example.data.source.remote.** { *; }

# Keep methods and constructors for these classes
-keepclassmembers class com.example.data.** {
    public *;
}

-keep class com.example.data.source.local.datastore.SettingsDataStore

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile