# Tealeaf

Tealeaf is a Kotlin utility library for Android that helps to reduce boilerplate.

## Features

Tealeaf currently provides:

 - Custom property delegates to reduce boilerplate when accessing views and fragment arguments.
 - Bundle extension methods to save additional standard types to Bundles.
 - Using (similar to try-with-resources) function for a range of types.
 - Misc other utility methods and extensions.

The library is intended to have no dependencies beyond the Kotlin standard library, the Android framework, and the
Android support libraries (ideally as an optional dependency).
 
### Property Delegates
 
#### Views

Activities, ViewGroups, and any class implementing the ViewDelegates.ViewHolder trait can use view delegates.

```kotlin
class MyActivity : Activity() {
  val textView: TextView by viewById(R.id.text)
  val button: Button by viewById(R.id.button)
  override fun onCreate(state: Bundle?) {
    super.onCreate(state)
    setContentView(R.layout.activity)
    textView.setText("Hello")
    button.setOnClickListener { textView.setText("Button clicked!") }
  }
```

As a Fragment's view may be destroyed and recreated within the fragment's life cycle (such as when the Fragment is
replaced and added to the back stack, then the back stack is popped), the following pattern can be used:

```kotlin
class MyFragment : Fragment() {
  class ViewHolder(override val view: View) : ViewDelegates.ViewHolder {
    val textView: TextView by viewById(R.id.text)
  }
  var views: ViewHolder? = null
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
    views = ViewHolder(inflater.inflate(R.layout.fragment, container, false))
    return views.view
  }
  override fun onDestroyView() {
    views = null
  }
```

#### Bundle (and Fragment arguments)

Bundle delegates back a property with a value in a Bundle. These can be useful in Fragments to retrieve argument
values.

```
class MyFragment : Fragment() {
  // when accessed, this will read the "arg" int value from the Fragment's arguments Bundle
  val arg: Int by argument()
```

By default property names are used as the Bundle key, however this can be changed. Default values can also be set.

Bundle delegates can be backed by any arbitrary Bundle, and are not limited to Fragment arguments.

### Using

Reduce the try { ... } finally { close() } boilerplate needed for disposable resources with the 'using' functions.

```kotlin
using (FileInputStream(File("file"))) {
  /* use the stream (e.g. this.read()) - it will be automatically closed after */
}
```

## Development Status

Tealeaf is still fairly early in development. The current features are tested, however the API is subject to change.

## License

    Copyright (C) 2015 Robert Carr

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
