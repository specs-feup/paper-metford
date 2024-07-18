# How to collect data

These steps assume that we have a project built with no errors, with all testing passing, and with all the external and internal dependencies generated, according the `README.md` file available at `subject` programs directory.

## Mutation Generation

Consider PRJ the variable containing the project directory of the product under testing. Examples assume Aeges project is the project under testing.

To run Kadabra for mutation generation, run the command below from `mutation-testing-v2` directory:

```
cd $HOME/feup/tools/mutation-testing-v2

/usr/bin/time -o aeges-schemata-generation.time --quiet -v -p java -jar ../bin/kadabra.jar -c Kadabra/aeges-schemata.kadabra

```

## Extraction of Mutant Statistics and IDs

Once mutation generation finishes, it creates a new directory with the mutated source files and a `MutationInfo.json` with mutant ids. The exact location of this files is configured inside `Kadabra/aeges-schemata.json` file. In our example, they are located at `$HOME/feup/tools/Projects/output-aeges-schemata`.

To extract the mutant IDs just run the scripts below:

```
cd $HOME/feup/tools/Projects/output-aeges-schemata

$HOME/feup/tools/scripts-kadabra/03-mutantions-info.py mutated_project/MutationInfo.json -sta
```

This command gave statistical information about the number of mutants by mutation operator.

```
Arithmetic Operator;224
NullIntentOperatorMutator;23
RandomActionIntentDefinitionOperatorMutator;74
InvalidKeyIntentOperatorMutator;18
BuggyGUIListenerOperatorMutator;82
NullValueIntentPutExtraOperatorMutator;24
IntentPayloadReplacementOperatorMutator;24
LengthyGUICreationOperatorMutator;16
FindViewByIdReturnsNullOperatorMutator;64
InvalidIDFindViewOperatorMutator;64
LengthyGUIListenerOperatorMutator;2
```

To collect the IDs, just run the same command with `-ids` at the end, instead of `-sta`. Observe that we redirect the output to `ids.txt` file once it will be required in the following commands.

```
cd $HOME/feup/tools/Projects/output-aeges-schemata

$HOME/feup/tools/scripts-kadabra/03-mutantions-info.py mutated_project/MutationInfo.json -idint > ids.txt
```

```
cd $HOME/feup/tools/Projects/output-aeges-traditional

$HOME/feup/tools/scripts-kadabra/03-mutantions-info.py mutated_project/MutationInfo.json -idstr > ids.txt
```

## Create the Sequence of Execution File

The first line of `ids.txt` file has ORIGINAL_PROGRAM as an ID to make it possible to always run the original program first.

### Calculate the number of IDs

cat ids.txt | wc

### Generate a random or a sequence list of numbers to run mutants:

https://www.random.org/sequences/

https://www.reformattext.com/sequential-number-generator.htm

Save the sequence in a file named random.txt/sequence.txt

For mutant execution, given a number interval, we select the IDs based on the random numbers in the random.txt file

We must include ID number 1 (ORIGINAL PROGRAM) as the first number in random number sequence such that, the original program is executed as a regular mutant.

Copy files ids.txt and random.txt to the project directory to run the follogin steps.

cp ids.txt random.txt ~/feup/tools/Projects/android/Aegis-schemata/

## Collect Time and Space for Mutation Generation

During mutant generation step, several files are created. The scripts below collected time and space information about mutation generation strategies.

### Mutant Schemata

For mutant schemata, we must run the following script to collect data:

cd ~/feup/tools/Projects/output-aeges-schemata
~/feup/tools/scripts-kadabra/04-mutant-generation-schemata-time-space.sh $PWD mutant-generation-schemata-time-space.csv


### Traditional Mutation

For traditional mutation, we must run the following script to collect data:

cd ~/feup/tools/Projects/output-aeges-traditional
~/feup/tools/scripts-kadabra/04-mutant-generation-traditional-time-space.sh $PWD mutant-generation-traditional-time-space.csv


In both cases, the generated files `mutant-generation-schemata-time-space.csv` and `mutant-generation-traditional-time-space.csv` must be post-processed to make the calculations about time and space consumption.

### Extract Test Case names from apks

#### Aegis

- Call emulator
- Install apk debug
- Install Android Test apk
- Run 

```
adb shell pm list instrumentation
```

The command above will provide output like:

```
instrumentation:com.beemdevelopment.aegis.debug.test/com.beemdevelopment.aegis.AegisTestRunner (target=com.beemdevelopment.aegis.debug)
```

This information is used in the command below.

```
adb shell am instrument -w -r --no-window-animation -e log true -e package com.beemdevelopment.aegis com.beemdevelopment.aegis.debug.test/com.beemdevelopment.aegis.AegisTestRunner > test-cases.info

python 02-extract-test-case-names.py test-cases.info > test-case-names.txt
```

- Save `test-case-names.txt` inside project directory



#### AntennaPod
- Build app using

```
cd ~/insync/Documentos/feup/tools/Projects/android/AntennaPod
./gradlew clean assembleFreeDebug assembleAndroidTest
```

- Call emulator (emulator or tested previously during project build)

```
emulator -avd Pixel_6_Pro_API_30 -wipe-data
```

- Install apk debug

```
adb install app/build/outputs/apk/free/debug/app-free-debug.apk
```

- Install Android Test apk

```
adb install app/build/outputs/apk/androidTest/free/debug/app-free-debug-androidTest.apk
```

- Run 

```
adb shell pm list instrumentation
```

The command above will provide output like:

```
instrumentation:de.danoeh.antennapod.core.tests/androidx.test.runner.AndroidJUnitRunner (target=de.danoeh.antennapod.debug)
```

This information is used in the command below.

```
adb shell am instrument -w -r --no-window-animation -e log true de.danoeh.antennapod.core.tests/androidx.test.runner.AndroidJUnitRunner > test-cases.info
```

```
~/feup/tools/scripts-kadabra/02-extract-test-case-names.py test-cases.info > test-case-names.txt
```

- Save `test-case-names.txt` inside project directory


#### AmazeFileManager
- Build app using

```
cd ~/insync/Documentos/feup/tools/Projects/android/AmazeFileManager
./gradlew clean assembleFdroidDebug assembleFdroidDebugAndroidTest
```

- Call emulator (emulator or tested previously during project build)

```
emulator -avd Pixel_6_Pro_API_30 -wipe-data
```

- Install apk debug

```
adb install ./app/build/outputs/apk/fdroid/debug/app-fdroid-debug.apk
```

- Install Android Test apk

```
adb install ./app/build/outputs/apk/androidTest/fdroid/debug/app-fdroid-debug-androidTest.apk
```

- Run 

```
adb shell pm list instrumentation
```

The command above will provide output like:

```
instrumentation:com.amaze.filemanager.debug.test/androidx.test.runner.AndroidJUnitRunner (target=com.amaze.filemanager.debug)
```

This information is used in the command below.

```
adb shell am instrument -w -r --no-window-animation -e log true com.amaze.filemanager.debug.test/androidx.test.runner.AndroidJUnitRunner > test-cases.info
```

```
~/feup/tools/scripts-kadabra/02-extract-test-case-names.py test-cases.info > test-case-names.txt
```

- Save `test-case-names.txt` inside project directory

Now, run tests and eliminaters from `test-case-names.txt` the SKIPPED tests.

```
./gradlew connectedFdroidDebugAndroidTest
```


#### Omni-Notes
- Build app using

```
cd ~/feup/tools/Projects/android/Omni-Notes
./gradlew clean assembleFossDebug assembleFossDebugAndroidTest
```

- Call emulator (emulator or tested previously during project build)

```
emulator -avd Pixel_6_Pro_API_30 -wipe-data
```

- Install apk debug

```
adb install ./omniNotes/build/outputs/apk/foss/debug/OmniNotes-fossDebug-6.2.8.apk
```

- Install Android Test apk

```
adb install ./omniNotes/build/outputs/apk/androidTest/foss/debug/omniNotes-foss-debug-androidTest.apk
```

- Run 

```
adb shell pm list instrumentation
```

The command above will provide output like:

```
instrumentation:it.feio.android.omninotes.foss.test/androidx.test.runner.AndroidJUnitRunner (target=it.feio.android.omninotes.foss)
```

This information is used in the command below.

```
adb shell am instrument -w -r --no-window-animation -e log true it.feio.android.omninotes.foss.test/androidx.test.runner.AndroidJUnitRunner > test-cases.info
```

```
~/feup/tools/scripts-kadabra/02-extract-test-case-names.py test-cases.info > test-case-names.txt
```

- Save `test-case-names.txt` inside project directory

Now, run tests and eliminaters from `test-case-names.txt` the SKIPPED tests.

```
./gradlew connectedFossDebugAndroidTest
```


#### simplenote
- Build app using

```
cd ~/feup/tools/Projects/android/simplenote-android
./gradlew clean :Simplenote:assembleDebug assembleDebugAndroidTest
```

- Call emulator (emulator or tested previously during project build)

```
emulator -avd Pixel_6_Pro_API_30 -wipe-data
```

- Install apk debug

```
adb install ./Simplenote/build/outputs/apk/debug/Simplenote-debug.apk
```

- Install Android Test apk

```
adb install ./Simplenote/build/outputs/apk/androidTest/debug/Simplenote-debug-androidTest.apk
```

- Run 

```
adb shell pm list instrumentation
```

The command above will provide output like:

```
instrumentation:com.automattic.simplenote.debug.test/com.automattic.simplenote.SimplenoteAppRunner (target=com.automattic.simplenote.debug)
```

This information is used in the command below.

```
adb shell am instrument -w -r --no-window-animation -e log true com.automattic.simplenote.debug.test/com.automattic.simplenote.SimplenoteAppRunner > test-cases.info
```

```
~/feup/tools/scripts-kadabra/02-extract-test-case-names.py test-cases.info > test-case-names.txt
```

- Save `test-case-names.txt` inside project directory

Now, run tests and eliminaters from `test-case-names.txt` the SKIPPED tests.

```
./gradlew connectedDebugAndroidTest
```


#### keepassdroid

- Build app using

```
cd ~/feup/tools/Projects/android/keepassdroid
./gradlew clean assembleGeneralDebug assembleGeneralDebugAndroidTest
```

- Call emulator (emulator or tested previously during project build)

```
emulator -avd Pixel_6_Pro_API_30 -wipe-data
```

- Install apk debug

```
adb install ./app/build/outputs/apk/general/debug/app-general-debug.apk
```

- Install Android Test apk

```
adb install ./app/build/outputs/apk/androidTest/general/debug/app-general-debug-androidTest.apk
```

- Run 

```
adb shell pm list instrumentation
```

The command above will provide output like:

```
instrumentation:com.keepassdroid.tests/androidx.test.runner.AndroidJUnitRunner (target=com.android.keepass)
```

This information is used in the command below.

```
adb shell am instrument -w -r --no-window-animation -e log true com.keepassdroid.tests/androidx.test.runner.AndroidJUnitRunner > test-cases.info
```

```
~/feup/tools/scripts-kadabra/02-extract-test-case-names.py test-cases.info > test-case-names.txt
```

- Save `test-case-names.txt` inside project directory

Now, run tests and eliminaters from `test-case-names.txt` the SKIPPED tests.

```
./gradlew connectedGeneralDebugAndroidTest
```



## Run a Set of Mutants

Mutation execution is a time consuming task. The scripts have some parameters to make it possible to run different configurations aiming at to collect data incrementaly if desired.

### Running Schemata Mutants

#### Running via Gradle

```
cd ~/feup/tools/Projects/android/Aegis-schemata

~/feup/tools/scripts-kadabra/05-mutant-exec-schemata-emulator.sh /home/auri/feup/tools/Projects/android/Aegis-schemata ids.txt random.txt 1 10
```

#### Running via adb

The main advantage to run via adb is that we have a fine grained execution process, i.e, we execute each mutant with each test case individually. It means that we may interrupt the test execution as soon as a test case fails (`fastFail` parameter). If you like to run each mutant with all test cases use `fullFail` instead.

We can also runs only a subset of mutants bu specifing TO and FROM parameters, numbers 3 and 5 in the example below. These means that we want to execute mutants from index 1 to 10 from the random.txt file. Based on these indexes we addressed which mutation ID is used for mutant execution.

```
cd ~/feup/tools/Projects/android/Aegis-schemata

~/feup/tools/scripts-kadabra/05-adb-mutant-exec-schemata-emulator.sh /home/auri/feup/tools/Projects/android/Aegis-schemata app/build/outputs/apk/debug/app-debug.apk app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk ids.txt random.txt 1 15 test-case-names.txt com.beemdevelopment.aegis.debug.test/com.beemdevelopment.aegis.AegisTestRunner fastFail 
```

### Running Traditional Mutants

#### Running via Gradle

```
cd ~/feup/tools/Projects/android/Aegis-traditional

~/feup/tools/scripts-kadabra/05-mutant-exec-traditional-emulator.sh /home/auri/feup/tools/Projects/android/Aegis-traditional /home/auri/feup/tools/Projects/output-aeges-traditional/mutated_project ids.txt random.txt 1 10
```

#### Running via adb

```
cd ~/feup/tools/Projects/android/Aegis-traditional

~/feup/tools/scripts-kadabra/05-adb-mutant-exec-traditional-emulator.sh /home/auri/feup/tools/Projects/android/Aegis-traditional /home/auri/feup/tools/Projects/output-aeges-traditional/mutated_project app/build/outputs/apk/debug/app-debug.apk app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk ids.txt random.txt 1 15 test-case-names.txt com.beemdevelopment.aegis.debug.test/com.beemdevelopment.aegis.AegisTestRunner fastFail 
```

In any case, theses scripts generate a set of files which makes it possible to evaluate, a posteori, the results of mutant execution. Files with `.out` extension have information about commands execution, and files with `.time` extension have information about time spend on different activities.

 - summary.out: contains a summary of mutation execution. Each line contains the mutant ID followwing by a serie ou integer numbers. 0 means test executes successfully on that mutant; !0 means a test failure (mutant killed)
 - adb-intall.out: output of adb install command
 - full-execution.time: detailed execution time of each mutant
 - mutant ID directory: individual test case execution result per mutant

 ## Signing apps

### Signing release

```
cd app/build/outputs/apk/release

zipalign -v -p 4 app-release-unsigned.apk app-unsigned-aligned.apk

apksigner sign --ks /home/auri/insync/keystores/android-release-key.jks --out app-release-signed.apk app-unsigned-aligned.apk

adb install app-release-signed.apk
```


### Signing test
```
cd app/build/outputs/apk/androidTest/release

zipalign -v -p 4 app-release-androidTest.apk app-release-androidTest-aligned.apk

apksigner sign --ks /home/auri/insync/keystores/android-release-key.jks --out app-release-androidTest-signed.apk app-release-androidTest-aligned.apk

adb install app-release-androidTest-signed.apk

adb shell pm list instrumentation
```