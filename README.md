[![](https://jitpack.io/v/sebschaef/LifecycleCollector.svg)](https://jitpack.io/#sebschaef/LifecycleCollector)

# LifecycleCollector
This library provides you convenience extension functions to easier collect Kotlin Flows
lifecycle-aware from an Android component, such as Activity or Fragments. It gives you a
more convenient way to collect Flows in LiveData-fashion.

## Examples
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate() {
        ...
        viewModel.someStateFlow.collectWhenStarted(owner = this) {
            binding.someTextView.text = it
        }
    }
}
```
```kotlin
viewModel.someFlow.collectWhenResumed(owner = viewLifecycleOwner) {
    Log.d("Collected item:", it)
}
```
Note: To recreate the same behaviour as with LiveData, use `collectWhenStarted()`.

## Setup
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    implementation 'com.github.sebschaef:LifecycleCollector:0.1.1'
}
```

## How it works
It uses currently the experimental `androidx.lifecycle:lifecycle-runtime-ktx` under the hood, which
supports the `repeatOnLifecycle()` functionality to match the same behaviour as with LiveData.

The convenience functions are no suspend functions and will handle the launch of a coroutine
themselves. They will launch it in the lifecycleScope of the current LifecycleOwner and only collect
the given flow inside the specific scope (CREATED, STARTED, RESUMED). When the scope is left, the
collection is canceled. If the LifecycleOwner re-enters the scope, the collection is restarted.

See the demo application for more details:

<img src="https://sebschaef.bitbucket.io/images/screen_lifecyclecollector.gif" width="250">