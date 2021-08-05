# SAF Test

The app uses a vulnerability of [Storage Access Framework](https://developer.android.com/guide/topics/providers/document-provider) to create a file named `MLGMXYYSD_SAFTEST.txt` in `<Internal Storage>/Android/data` or `<Internal Storage>/Android/obb`.

This file lists all the files in the directory.

This kind of behavior will destroy [Package visibility](https://developer.android.com/about/versions/11/privacy/package-visibility) introduced in Android 11.

Initially, it was used for [Accessing Android/data through SAF in Android R](https://mlgmxyysd.meowcat.org/2021/02/18/android-r-saf-data/)'s PoC.

Prebuilt APK in [app/release/app-release.apk](app/release/app-release.apk).

## Test

- AVD x86_64: Google Play SPP1.210122.020.A3 Android 12 (S Developer Preview 1)
- Pixel 4 XL: Material You SPB3.210618.016 Android 12 (Beta 3.1)
- OnePlus 8T: Hydrogen OS 11.0.9.9.KB05 Android 11