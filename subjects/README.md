# Programs Used in the Experiment

## Android Java Projects

	- Running testing from command line on emulator:
		- Start emulator via CMD
			emulator -avd Pixel_7_Pro_API_30

		- Run
			./gradlew cAT
			or
			./gradlew connectedAndroidTest

## General hints to test mobile apps

### Resolve all dependency problem

Open the project in Android Studio and make sure it compiles and run all tests accordantly.

After this step, click over the project and run "Analyse Dependencies...". Resolve all problems before continue.

One possible way to resolve dependencies is to force Gradle to always use a specific version of a given library. Just includes a section like the one below in the end of `app/build.gradle` :

```
configurations.all {
    resolutionStrategy {
        force 'com.google.auto.value:auto-value-annotations:1.10.1'
        ...
    }
}
```

After dependency resolution make sure the app is running and all tests also run successfuly.

### Include a task for export external depencencies

Inside `android {` configuration in `app/build.gradle` file, includes the code task below

```
	configurations {
		resolvedImplementation.extendsFrom(implementation).canBeResolved = true	
	}
	task downloadDependencies(type: Copy) {
		from configurations.resolvedImplementation
		into './dependenciesDirectory'
	}

```

It may be necessary to remove some subpackage to make it works. This is specially true for projects with several modules.
For this case, use the template below:

```
    configurations {
        resolvedImplementation.extendsFrom(implementation{
            exclude(module: "commons_compress_7z")
            exclude(module: "file_operations")
            exclude(module: "portscanner")            
        }).canBeResolved=true
    }
    task downloadDependencies(type: Copy) {
        from configurations.resolvedImplementation
        into './dependenciesDirectory'
    }
``` 

By running `gradlew` with this, an `app/dependenciesDirectory` directory is created containing all depencencies tha app requires to run.

```
./gradlew downloadDependencies
```

The next step is to convert all `.aar` file to a `.jar` file once Kadabra uses only `.jar` files.

To do this conversion just run the script below indicating the app project directory as parameter:

```
~/feup/tools/scripts-kadabra/01-aar2jar.sh ~/feup/tools/Projects/android/Aegis
```

### Configuring the CLASSPATH

Besides all external libraries, we also need to include in the classpath some paths to app files and also, the path to `android.jar` file according to the project specification. For instance, for Aegis app, the following paths are required:


```
$HOME/feup/tools/Projects/android/Aegis/app/build/intermediates/compile_and_runtime_not_namespaced_r_class_jar/debug/R.jar

$HOME/feup/tools/Projects/android/Aegis/app/build/intermediates/javac/debug/classes

$HOME/libs/android33
```

Observe that, instead of using the `android.jar` file, you need to uncompress this file and exclude `java` and `javax`subfolders. 

For instance, the below example is the android-33 subfolders. The path to this folder should be in the classpath also.

```
android33/
├── android
├── AndroidManifest.xml
├── androidx
├── assets
├── dalvik
├── META-INF
├── NOTICES
├── org
├── res
└── resources.arsc
```

All paths must be combined in a single line using a `;` as path separator, independently of underline Operation System (OS). Kadabra processes this classpath assuming it is a semi-colon separated path list.

To help the build of the external libraries paths use the scripts below. The script must be executes inside the directory contaning the external `.jar` files:

```
cd Aegis/app/dependenciesDirectory

~/feup/tools/scripts-kadabra/02-generate-kadabra-classpath.sh
```

Then, combine the output with the other app paths presented above.

This classpath is set as the `"classpath"` field in the Kadabra `.json` app configuration file.

### Include failFast options at Unit and Instrumented Tests

```
android {
...
	testOptions {
        execution 'ANDROIDX_TEST_ORCHESTRATOR'
        animationsDisabled true

        unitTests {
        	...
            all {
                failFast true
                ignoreFailures false
            }
            returnDefaultValues true // added AURI - Avoid mock errors
        ...
    }
}


import org.gradle.api.tasks.StopExecutionException
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestResult

// Custom implementation to stop tests on first failure
ext {
    hasTestFailed = false
}

gradle.addListener(new TestListener() {
    void beforeSuite(TestDescriptor suite) {
        if (suite.parent == null) {  // root suite
            hasTestFailed = false
        }
    }

    void afterSuite(TestDescriptor suite, TestResult result) {
        if (suite.parent == null) {  // root suite
            if (result.failedTestCount > 0) {
                hasTestFailed = true
            }
        }
    }

    void beforeTest(TestDescriptor testDescriptor) {
        if (hasTestFailed) {
            throw new StopExecutionException()
        }
    }

    // Empty implementation of other required methods
    void afterTest(TestDescriptor testDescriptor, TestResult result) { }
    void beforeTestClass(TestDescriptor testDescriptor) { }
    void afterTestClass(TestDescriptor testDescriptor, TestResult result) { }
})

project.afterEvaluate {
    tasks.named('connectedAndroidTest').configure {
        doFirst {
            if (hasTestFailed) {
                throw new StopExecutionException()
            }
        }
    }
}
```

### Improve gradlew Test Output 

Code below must be included, in general, in the end of `app/build.gradle` file.

This options allows both, a better output for data collection from gradlew output, and to make all tests run again on each call of `./gradlew test` command.

```
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

tasks.withType(Test) {
    testLogging {
        // set options for log level LIFECYCLE
        events TestLogEvent.FAILED,
               TestLogEvent.PASSED,
               TestLogEvent.SKIPPED,
               TestLogEvent.STANDARD_OUT
        exceptionFormat TestExceptionFormat.FULL
        showExceptions true
        showCauses true
        showStackTraces true

        // set options for log level DEBUG and INFO
        debug {
            events TestLogEvent.STARTED,
                   TestLogEvent.FAILED,
                   TestLogEvent.PASSED,
                   TestLogEvent.SKIPPED,
                   TestLogEvent.STANDARD_ERROR,
                   TestLogEvent.STANDARD_OUT
            exceptionFormat TestExceptionFormat.FULL
        }
        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat

        afterSuite { desc, result ->
            if (!desc.parent) { // will match the outermost suite
                def output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped)"
                def startItem = '|  ', endItem = '  |'
                def repeatLength = startItem.length() + output.length() + endItem.length()
                println('\n' + ('-' * repeatLength) + '\n' + startItem + output + endItem + '\n' + ('-' * repeatLength))
            }
        }
    }
}

tasks.withType(Test).configureEach {
  outputs.upToDateWhen { false }
}
```

It is important to ensure all test cases have a timeout. Some mutants may generate an infinite looping. Timeout is important to continue testing even in the presence of looping.

In Aegis, we included a `@Rule` at `app/src/androidTest/java/com/beemdevelopment/aegis/AegisTest.java` file.

```
...

import org.junit.Rule;
import org.junit.rules.Timeout;
import java.util.concurrent.TimeUnit;
...
public abstract class AegisTest {
    @Rule public Timeout timeout = new Timeout(240_000, TimeUnit.MILLISECONDS);
...
```


## Projects

### Aegis - Version 2.1.3 - last version compatible with JDK 11

 - git clone https://github.com/beemdevelopment/Aegis.git
 - git checkout v2.1.3
 - git switch -c v2.1.3
 - git status

 - Requires JDK 11

 - Build with 
 	./gradlew compileDebugJavaWithJavac

 - Running unit testing
 ```
 ./gradlew test
 ```

 Some tests fail and are annotated with `@Ignore`
 
 SUCCESS (57 tests, 50 passed, 0 failed, 7 skipped)

 ```
	app/src/test/java/com/beemdevelopment/aegis/crypto/SCryptTest.java
	app/src/test/java/com/beemdevelopment/aegis/importers/DatabaseImporterTest.java
	app/src/test/java/com/beemdevelopment/aegis/otp/YandexInfoTest.java
	app/src/test/java/com/beemdevelopment/aegis/vault/slots/SlotTest.java
 ```

 - Running successfull in Android Studio Giraffe | 2022.3.1 Patch 2
  	- Emulator Pixel 7 Pro API 30

 - Running successfull in CMD
  	- Emulator Pixel 7 Pro API 30

 - Running testing from command line on emulator:
		- Start emulator via CMD
			emulator -avd Pixel_7_Pro_API_30

		- Run
			./gradlew cAT
			or
			./gradlew connectedAndroidTest  	


### AmazeFileManager - Version 3.8.4
 - git clone https://github.com/TeamAmaze/AmazeFileManager.git
 - git checkout v3.8.4
 - git switch -c v3.8.4
 - git status

 - Uses Gradle 7.4 - JDK 11
 - Upgradle to Gradle 7.4.2 using Android Studio AGP

 - Build successfull
``` 	
    ./gradlew assembleDebug assembleAndroidTest
```     
 - Removed test sets due to errors:

 - Running successfull in Android Studio Giraffe | 2022.3.1 Patch 2
  	- Emulator Pixel_7_Pro_API_31
        - All test run successfuly inside Android Studio

 - Build app and test for experimentation using:
```     
 	./gradlew clean assembleFdroidDebug
    ./gradlew assembleFdroidDebugAndroidTest
```     

 - Run experiments with tasks:

```     
    ./gradlew connectedFdroidDebugAndroidTest
```     


### AntennaPod - Version Latest on 2023-11-14 (commit 0bb4870820cfe279e56f02d453c532ebb5074d6a)
 - git clone https://github.com/AntennaPod/AntennaPod.git
 - git log

    ```
    commit 0bb4870820cfe279e56f02d453c532ebb5074d6a (HEAD -> develop, origin/develop, origin/HEAD)
    Author: ByteHamster <ByteHamster@users.noreply.github.com>
    Date:   Tue Nov 14 21:12:03 2023 +0100

        Be more aggressive about telling users to also search closed issues (#6762)
    ```

 - git status
 - git describe --tags

 - Uses Gradle 8.1.1 - JDK 11

 - Build with:
    ```
    ./gradlew build 
    ```

    This command ended with signature error

    ```
    > Task :app:validateSigningFreeRelease FAILED

    FAILURE: Build failed with an exception.

    * What went wrong:
    Execution failed for task ':app:validateSigningFreeRelease'.
    > Keystore file '/home/auri/insync/Documentos/feup/tools/Projects/android/AntennaPod/app/keystore' not found for signing config 'releaseConfig'.
    ```

 - Tests run with failures on Android Studio
   - Emulator Pixel 7 Pro API 33 (103 tests - 8 failed)

   - Emulator Pixel 7 Pro API 32 (103 tests - 8 failed)

   - Emulator Pixel 7 Pro API 31 (103 tests - 7 failed)

   - Emulator Pixel 7 Pro API 30 (103 tests - 5 failed)

   - Emulator Pixel 7 Pro API 29 (103 tests - 5 failed)

   - Emulator Pixel 6 Pro API 30 (103 tests - 5 failed)
```
emulator -avd Pixel_6_Pro_API_30 -wipe-data
```

   - 103 tests: 98 passed - 5 failed (failed test was ignored with @Ignore)

```
./gradlew :app:connectedFreeDebugAndroidTest
```

To generate the R.jar files use the command below:

```
./gradlew clean assembleFreeDebug
```

Then, by running 

```
find . -name "R.jar"
```

you will discover all R.jar file that must be copied to libs-jar directory. Observe that we create an script to rename R.jar files in case projects with subprojects such that there is no colision with the same file name.

Beides `java` and `javax`, it is also necessary to remove `org.w3c` from `android33` due to duplicity detected by `spoon`.

### Omni-Notes - Version 6.2.9

 - git clone https://github.com/federicoiosue/Omni-Notes.git
 - git checkout 6.2.9
 - git switch -c 6.2.9
 - git status

- Uses Gradle 8.0 - JDK 11


 - Build with:
```
 	./gradlew clean assembleFossDebug assembleFossDebugAndroidTest
```

 - Run tests with - Pixel_6_Pro_API_30
 ```
 ./gradlew connectedFossDebugAndroidTest
 ```

### simplenote-android - Version 2.30

 ```
 git clone https://github.com/Automattic/simplenote-android.git
 git checkout 2.30
 git switch -c 2.30
 git status
```

Version used is:

```
No ramo 2.30
nothing to commit, working tree clean
```

- Uses Gradle 6.1.1 - JDK 11

Build with:

```
    ./gradlew clean :Simplenote:assembleDebug
```

 - Resolve all dependecy conflicts with Android Studio tool (see how to in the begining of this document)

 - Generate all dependencies:

```
cd ~/feup/tools/Projects/android/simplenote-android
~/feup/tools/scripts-kadabra/01-aar2jar.sh $PWD
~/feup/tools/scripts-kadabra/01-r-jar-rename.sh $PWD
~/feup/tools/scripts-kadabra/01-copy-project-class-files.sh $PWD classes javac
~/feup/tools/scripts-kadabra/01-copy-project-class-files.sh $PWD debug kotlin-classes
mv libs-jar/debug libs-jar/kotlin-classes
```

 - Requires SDK 33

```
cd ~/feup/tools/Projects/android/simplenote-android/libs-jar
cp -r ~/Android/Sdk/platforms/android-33/android.jar .
mkdir android-33
cd android-33
jar -xvf ../android.jar
rm ../android.jar
rm -rf java javax
```

 - Run tests with - Pixel_6_Pro_API_30
 ```
 ./gradlew assembleDebugAndroidTest
 ./gradlew connectedDebugAndroidTest
 ```

 - Generate classpath

```
 cd ~/feup/tools/Projects/android/simplenote-android
 mkdir ~/libs-sn
 cp -r libs-jar/* ~/libs-sn/
 cd ~/libs-sn/
 ~/feup/tools/scripts-kadabra/02-generate-kadabra-classpath.sh
```

Get the resultant output to create the Kadabra classpath to be included in the .json file

```
"classpath":"/home/auri/libs-sn/android-33/;/home/auri/libs-sn/classes/;/home/auri/libs-sn/kotlin-classes/;/home/auri/libs-sn/activity-1.3.1.jar;...
```

### keepassdroid - Version a9e673c5b361a02c1f5a276e34f46bd24c360811

 ```
 git clone https://github.com/bpellin/keepassdroid.git
 git log --pretty="%H - %cd" -n 1
```

Version used is:

```
a9e673c5b361a02c1f5a276e34f46bd24c360811 - Fri Aug 18 23:06:00 2023 -0500
```

- Uses Gradle 7.5 - JDK 11

Build with:

```
    ./gradlew clean assembleGeneralDebug
```

 - Resolve all dependecy conflicts with Android Studio tool (see how to in the begining of this document)

 - Generate all dependencies:

```
cd ~/feup/tools/Projects/android/keepassdroid
./gradlew clean assembleGeneralDebug
./gradlew downloadDependencies
~/feup/tools/scripts-kadabra/01-aar2jar.sh $PWD
~/feup/tools/scripts-kadabra/01-r-jar-rename.sh $PWD
~/feup/tools/scripts-kadabra/01-copy-project-class-files.sh $PWD classes javac
~/feup/tools/scripts-kadabra/01-copy-project-class-files.sh $PWD debug kotlin-classes
mv libs-jar/debug libs-jar/kotlin-classes
```

 - Requires SDK 33

```
cd ~/feup/tools/Projects/android/keepassdroid/libs-jar
cp -r ~/Android/Sdk/platforms/android-33/android.jar .
mkdir android-33
cd android-33
jar -xvf ../android.jar
rm ../android.jar
rm -rf java javax org/w3c
cd ~/feup/tools/Projects/android/keepassdroid/
```

 - Run tests with - Pixel_6_Pro_API_30
 ```
 ./gradlew assembleGeneralDebugAndroidTest
 ./gradlew connectedGeneralDebugAndroidTest
 ```

 - Generate classpath

```
cp -r ~/feup/tools/Projects/android/keepassdroid/libs-jar ~/libs-ke
cd ~/libs-ke
~/feup/tools/scripts-kadabra/02-generate-kadabra-classpath.sh
```

The genrated path should be used in the kadabra json configuration file.



### CameraView - Version b279ffaf57919b7dffccc8879a0cb1eeb542c1ca

 ```
 git clone https://github.com/natario1/CameraView.git
 git log --pretty="%H - %cd" -n 1
```

Version used is:

```
b279ffaf57919b7dffccc8879a0cb1eeb542c1ca - Wed Apr 5 15:03:18 2023 +0200
```

- Uses Gradle 7.2 - JDK 17

Build with:

```
    ./gradlew clean assembleDebug
```

 - Resolve all dependecy conflicts with Android Studio tool (see how to in the begining of this document)

 - Generate all dependencies:

```
cd ~/feup/tools/Projects/android/keepassdroid
./gradlew clean assembleGeneralDebug
./gradlew downloadDependencies
~/feup/tools/scripts-kadabra/01-aar2jar.sh $PWD
~/feup/tools/scripts-kadabra/01-r-jar-rename.sh $PWD
~/feup/tools/scripts-kadabra/01-copy-project-class-files.sh $PWD classes javac
~/feup/tools/scripts-kadabra/01-copy-project-class-files.sh $PWD debug kotlin-classes
mv libs-jar/debug libs-jar/kotlin-classes
```

 - Requires SDK 31

```
cd ~/feup/tools/Projects/android/CameraView/libs-jar
cp -r ~/Android/Sdk/platforms/android-31/android.jar .
mkdir android-31
cd android-31
jar -xvf ../android.jar
rm ../android.jar
rm -rf java javax org/w3c
cd ~/feup/tools/Projects/android/CameraView/
```

 - Run tests with - Pixel_6_Pro_API_30
 ```
 ./gradlew assembleDebugAndroidTest
 ./gradlew connectedDebugAndroidTest
 ```

 - Generate classpath

```
cp -r ~/feup/tools/Projects/android/keepassdroid/libs-jar ~/libs-ke
cd ~/libs-ke
~/feup/tools/scripts-kadabra/02-generate-kadabra-classpath.sh
```

The genrated path should be used in the kadabra json configuration file.