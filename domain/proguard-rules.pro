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
# Keep Dagger-Hilt related annotations
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep @interface dagger.hilt.**
-keep @interface javax.inject.**

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
-repackageclasses 'com.example.obfuscated.domain'

-keep class com.example.domain.model.** { *; }
-keep class com.example.domain.repository.**{*;}
-keep class com.example.domain.usecase.**{*;}


# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile