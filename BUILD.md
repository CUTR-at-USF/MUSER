# Building the project

We use [Gradle build flavors](http://developer.android.com/tools/building/configuring-gradle.html#workBuildVariants) to enable a number of different build variants of MUSER.

MUSER is based on the open-source [Shuttle](https://github.com/timusus/Shuttle) app. Shuttle defines a number of variants along a single dimension `all` in `build.gradle` and then filters out some of the combined variants programmatically in `build.gradle` for those that don't make sense (e.g., `devRelease`).

The list of all possible build variants that are used in Shuttle are:

* `amazonFreeRelease`
* `amazonPaidRelease`
* `devDebug`
* `freeRelease`
* `paidRelease`

But, in the case of MUSER, we only use the `devDebug` amd `freeRelease` variants for debug and release variants, respectively.

The below instructions assume you're going to be building for the `debug` variant for testing on Google devices (i.e., `devDebug`).

### Prerequisites for both Android Studio and Gradle

1. Clone this repository
1. Install [Java Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

### Building in Android Studio

1. Download, install, and run the latest version of [Android Studio](http://developer.android.com/sdk/installing/studio.html).
1. At the welcome screen select `Import Project`, browse to the location of this repository and select it then select Ok.
1. Open the Android SDK Manager (Tools->Android->SDK Manager) and add a checkmark for the necessary API level (see `compileSdkVersion` in [`MUSER/build.gradle`](MUSER/build.gradle)) then select OK.
1. Connect a [debugging enabled](https://developer.android.com/tools/device.html) Android device to your computer or setup an Android Virtual Device (Tools->Andorid->AVD Manager).
1. Open the "Build Variants" window (it appears as a vertical button on left side of workspace by default) & choose **devDebug** to select the Google Play version.
1. Click the green play button (or Alt+Shift+F10) to build and run the project!

### Building from the command line using Gradle

1. Set the `JAVA_HOME` environmental variables to point to your JDK folder (e.g. `C:\Program Files\Java\jdk1.7.0_27`)
1. Download and install the [Android SDK](http://developer.android.com/sdk/index.html). Make sure to install the Google APIs for your API level (e.g. 17), the Android SDK Build-tools version for your `buildToolsVersion` version, the Android Support Repository and the Google Repository.
1. Set the `ANDROID_HOME` environmental variable to your Android SDK location.
1. To build and push the app to the device, run `gradlew installdevDebug` from the command line at the root of the project.
1. To start the app, run `adb shell am start -n com.simplecity.amp_library.ui.screen.main.MainActivity` (alternately, you can manually start the app)


### Release builds

To build a release build, you need to create a `gradle.properties` file that points to a `secure.properties` file, and a `secure.properties` file that points to your keystore and alias.

The `gradle.properties` file is located in the `MUSER` directory and has the contents:
```
secure.properties=<full_path_to_secure_properties_file>
```

The `secure.properties` file (in the location specified in gradle.properties) has the contents:
```
key.store=<full_path_to_keystore_file>
key.alias=<key_alias_name>
key.storepassword=<your_keystore_password>
key.keypassword=<your_key_password>
```

Note that the paths in these files always use the Unix path separator `/`, even on Windows. If you use the Windows path separator `\` you will get the error `No value has been specified for property 'signingConfig.keyAlias'.`

In MUSER we use the `freeRelease` variant for releases, which has been modified to include features normally restricted to the paid release variant in Shuttle. To build this release variant, do:

`gradlew assembleFreeRelease`

..or if you don't want to use the command line, then you can select the appropriate build variant from left task bar of the IDE and select the variant you would like. Then on the top menu, go to Build --> Build Bundles or APKs --> Build APK
