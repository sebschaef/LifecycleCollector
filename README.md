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
    implementation 'com.github.sebschaef:LifecycleCollector:0.2.2'
}
```

## How it works
It uses currently the experimental `androidx.lifecycle:lifecycle-runtime-ktx` under the hood, which
supports the `repeatOnLifecycle()` functionality to restart a flow collection after it left the
wanted lifecycle scope and re-enters it.

The convenience functions are no suspend functions and will handle the launch of a Coroutine
themselves. They will launch it in the `lifecycleScope` of the current `LifecycleOwner` and only
collect the given flow inside the specific scope (`CREATED`, `STARTED`, `RESUMED`). When the scope
is left, the collection is canceled. If the `LifecycleOwner` re-enters the scope, the collection is
restarted.

See the demo application for more details:

<img src="https://sebschaef.bitbucket.io/images/screen_lifecyclecollector.gif" width="250">

## License
```
Copyright 2021 Sebastian Schäf

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```