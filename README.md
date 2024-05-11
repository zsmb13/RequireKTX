# requireKTX

**requireKTX** is a collection of small utility functions to make it easier to work with **values that should always exist** on Android and Kotlin Multiplatform, in the style of [`requireContext`](https://developer.android.com/reference/androidx/fragment/app/Fragment.html#requireContext()), [`requireArguments`](https://developer.android.com/reference/androidx/fragment/app/Fragment.html#requireArguments()), and other similar Android SDK methods.

Types that requireKTX provides extensions for:

- [Bundle](#bundle) (Kotlin Multiplatform)
- [NavBackStackEntry](#navbackstackentry) (Kotlin Multiplatform)
- [Intent](#intent) (Android)
- [WorkManager Data](#workmanager-data) (Android)

## Why?

Take the example of grabbing a Bundle and reading a String ID from it that should always be there: the Bundle APIs give you a nullable result, which means you'll have to do some kind of null handling.

```kotlin
// Without requireKTX 😥
val id: String = argumentBundle.getString("user_id")!!
```

The exception potentially thrown by this code also won't be too helpful in tracking down the problem, as it won't tell you details such as whether the value was missing, or if it was the wrong type for the request.

Another problem with Bundles is accessing primitive values, as they're always returned as non-nullable, defaulting to `0` (or even worse, `false` for Booleans) if the key is not found or its associated value has the wrong type:

```kotlin
val bundle = Bundle()
bundle.putDouble("count", 123.0)

// These both pass 😱
assertEquals(0, bundle.getInt("count"))
assertEquals(0, bundle.getInt("score"))
```

This makes it difficult to know if what you received was a real `0` value, or if something silently went wrong.

---

Instead of using these methods, requireKTX provides extensions such as `requireString`, which you can use to *require* a value that must always be there:

```kotlin
// With requireKTX 🥳
val id: String = argumentBundle.requireString("user_id")
val count: Int = argumentBundle.requireInt("count")
```

These methods give you non-nullable return types. If the key isn't set or the value doesn't have the expected type, they throw meaningful exceptions based on the error that occurred. This is true for accessing primitive values as well.

### getOrNull

requireKTX **also includes `getOrNull` style methods for everything that it covers with `require` style methods**,to make the nullable case more obvious and explicit. These match the conventions of the Kotlin Standard Library, and can make it clearer that `null` is returned if a value for a key couldn't be fetched.

```kotlin
val userId: String? = requireArguments().getStringOrNull("user_id")
val count: Int? = requireArguments().getIntOrNull("count")
```

## Dependencies

requireKTX is published on [Maven Central](https://repo1.maven.org/maven2/co/zsmb/).

```kotlin
repositories {
    mavenCentral()
}
```

There are several artifacts you can import depending on which types you want to get extensions for - see the module descriptions below to learn more.

```kotlin
dependencies {
    // commonMain or Android
    implementation("co.zsmb:requirektx-bundle:2.0.0-alpha01")
    implementation("co.zsmb:requirektx-navigation:2.0.0-alpha01")

    // Android only
    implementation("co.zsmb:requirektx-intent:2.0.0-alpha01")
    implementation("co.zsmb:requirektx-work:2.0.0-alpha01")
}
```

## Available modules and extensions

### Bundle

*The `requirektx-bundle` artifact works with the `androidx.core.bundle.Bundle` type, available on Android and other Kotlin Multiplatform targets from `org.jetbrains.androidx.core:core-bundle`.*

Given a `Bundle`, you can require the following types of values:

```kotlin
// Primitives (examples)
bundle.requireBoolean()
bundle.requireByte()
bundle.requireChar()
bundle.requireDouble()
bundle.requireFloat()

// Reference types
bundle.requireString()
bundle.requireBundle()
bundle.requireCharSequence()
bundle.requireParcelable()
bundle.requireSerializable()

// Arrays (examples)
bundle.requireBooleanArray()
bundle.requireByteArray()
bundle.requireCharArray()
bundle.requireDoubleArray()
bundle.requireFloatArray()
```

... and many more!

### NavBackStackEntry

*The `requirektx-navigation` artifact works with the `androidx.navigation.NavBackStackEntry` type, available on Android and other Kotlin Multiplatform targets from `org.jetbrains.androidx.navigation:navigation-runtime`.*

*This is compatible with both the [Jetpack Navigation component on Android](https://developer.android.com/guide/navigation) (with or without Compose) and the [Compose Multiplatform navigation library](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html).*

To get the bundle of arguments from an entry, use `requireArguments`:

```kotlin
val args: Bundle = navBackStackEntry.requireArguments()
```

Here's an example of using this with Compose, in combination with the [Bundle extensions](#bundle):

```kotlin
composable("detail/{objectId}") { backStackEntry ->
    val args = backStackEntry.requireArguments()
    val objectId = args.requireInt("objectId")
    DetailScreen(navController, objectId)
}
```

### Intent

*The `requirektx-intent` artifact works with the [`android.content.Intent`](https://developer.android.com/reference/android/content/Intent) type, available on Android only.*

Given an `Intent`, you can require its extras `Bundle` (and then require values from it as seen above):

```kotlin
val extras: Bundle = intent.requireExtras()
```

Or you can require specific extras directly for various types of values:

```kotlin
// Primitives (examples)
intent.requireBooleanExtra()
intent.requireByteExtra()
intent.requireCharExtra()
intent.requireDoubleExtra()
intent.requireFloatExtra()

// Reference types
intent.requireStringExtra()
intent.requireBundleExtra()
intent.requireCharSequenceExtra()
intent.requireParcelableExtra()
intent.requireSerializableExtra()

// Arrays (examples)
intent.requireBooleanArrayExtra()
intent.requireByteArrayExtra()
intent.requireCharArrayExtra()
intent.requireDoubleArrayExtra()
intent.requireFloatArrayExtra()
```

... and many more!

### WorkManager Data

*The `requirektx-work` artifact provides extensions for the [`androidx.work.Data`](https://developer.android.com/reference/androidx/work/Data) type, available on Android only.*

Given a WorkManager `Data` object (such as `inputData` inside a worker), you can require the following types of values:

```kotlin
class SomeWorker : Worker() {
    override fun doWork(): Result {
        // Values
        inputData.requireBoolean()
        inputData.requireByte()
        inputData.requireDouble()
        inputData.requireFloat()
        inputData.requireInt()
        inputData.requireLong()
        inputData.requireString()

        // Arrays
        inputData.requireBooleanArray()
        inputData.requireByteArray()
        inputData.requireDoubleArray()
        inputData.requireFloatArray()
        inputData.requireIntArray()
        inputData.requireLongArray()
        inputData.requireStringArray()

        // ...
    }
}
```

## License

    Copyright 2021 Márton Braun

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
