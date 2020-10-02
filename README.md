### MUSER

[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](LICENSE.md)
[![API](https://img.shields.io/badge/API-16%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![Android CI](https://github.com/CUTR-at-USF/MUSER/workflows/Android%20CI/badge.svg)](https://github.com/CUTR-at-USF/MUSER/actions?query=workflow%3A%22Android+CI%22)

An Android app used in USF research on music therapy. Based on the open-source [Shuttle](https://github.com/timusus/Shuttle) app.

#### Development

We use the `devDebug` variant for development and testing

#### Release

To create a new release, run the following command on the command line:
`gradlew assembleFreeRelease`

Then, upload the `app-free-release.apk` to the Google Play Console.

#### License

MUSER is released under the GNU General Public License v3.0 (GPLv3), which can be found here: [License](LICENSE.md). It's based on the the open-source [Shuttle](https://github.com/timusus/Shuttle) app, which is also licensed under GPLv3.
