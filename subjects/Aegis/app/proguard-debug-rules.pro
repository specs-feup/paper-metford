-keepclasseswithmembers public class androidx.recyclerview.widget.RecyclerView { *; }
-keep class com.beemdevelopment.aegis.ui.fragments.preferences.*
-keep class com.beemdevelopment.aegis.importers.** { *; }
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }

-dontobfuscate
-dontwarn javax.naming.**

# Keep test runner class
-keep class com.beemdevelopment.aegis.AegisTestRunner { *; }

# Keep test-related classes and methods
-keep class com.beemdevelopment.aegis.test.* { *; }
-keepclassmembers class com.beemdevelopment.aegis.test.* { *; }

#-keep class androidx.tracing.* { *; }
#-keepclassmembers class androidx.tracing.* { *; }

-keep @interface androidx.test.* {*;}
#-keep @interface androidx.tracing.* {*;}