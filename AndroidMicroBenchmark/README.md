# Android Micro Benchmark
This repository contains the source code of the application developed to evaluate the best alternative to Kadabra's implementation. The application evaluates the execution time in milliseconds (ms) considering four different strategies:

- static non-final int, property, switch/default
- static final int, property, if/else
- static final String, property, switch/default
- static final int, property default

To build the application, use the following command:
```
./gradlew build
```

The app is designed to compile using three versions of Java: 11, 17, and 21.

## Running the Micro Benchmark
1. Generate the apk file of the application
   ```
   ./gradlew assemble
   ```
2. Start the emulator. For this experimental study, we used the emulator **Pixel 6 Pro API 30**.
    ```
    emulator -avd Pixel_6_Pro_API_30
    ```
3. Install the application
    ```
    adb install <PATH_TO_APK>
    ```
4. Start the logcat to see the output. The results are displayed with the "BENCHMARK" tag
    ```
    adb logcat | grep BENCHMARK
    ``` 

5. Set the property *debug.INT_MUID* and *debug.STRING_MUID*
    ```
    adb shell setprop debug.INT_MUID 100
    adb shell setprop debug.STRING_MUID 100
    ```

6. Start the application and click on **RUN BENCHMARK**. Check the results displayed on the logcat. For example:
   ```
   04-24 17:11:33.198  5804  5841 D BENCHMARK: : Experiment 'static non-final int, property, switch' took 58ms
   04-24 17:11:33.265  5804  5841 D BENCHMARK: : Experiment 'static non-final int, property, default' took 66ms

   04-24 17:11:33.322  5804  5841 D BENCHMARK: : Experiment 'static final int, property, first if path' took 57ms
   04-24 17:11:33.818  5804  5841 D BENCHMARK: : Experiment 'static final int, property, else path' took 495ms

   04-24 17:11:34.157  5804  5841 D BENCHMARK: : Experiment 'static final String, property, switch' took 339ms
   04-24 17:11:34.554  5804  5841 D BENCHMARK: : Experiment 'static final String, property, default' took 396ms
   
   04-24 17:11:34.607  5804  5841 D BENCHMARK: : Experiment 'static final int, property, switch' took 52ms
   04-24 17:11:34.668  5804  5841 D BENCHMARK: : Experiment 'static final int, property, default' took 61ms
   ```