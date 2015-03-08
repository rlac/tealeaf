package au.id.rlac.tealeaf.properties

import android.app.Fragment
import android.support.v4.app.Fragment as SupportFragment
import kotlin.properties.ReadOnlyProperty
import android.os.Bundle
import android.os.Parcelable
import android.os.Parcel
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import java.io.Serializable

/* Kotlin Map style property delegates backed by a Bundle. */

/**
 * Delegates to a value provided by the Fragment arguments.
 *
 * @param key Called to retrieve the Bundle key. By default this is the property name.
 * @param default The default value. By default throws NoDefaultValueException.
 */
public fun Fragment.argument<V>(key: (PropertyMetadata) -> String = propertyNameKeyProvider,
                                default: (Any?, String) -> V = throwIfNoValue): ReadOnlyProperty<Any?, V> =
    KeyValueVal(bundleFromArguments, key, default, bundleContains, bundleDefaultRead)

/**
 * Delegates to a value provided by the support library Fragment arguments.
 *
 * @param key Called to retrieve the Bundle key. By default this is the property name.
 * @param default The default value. By default throws NoDefaultValueException.
 */
public fun SupportFragment.argument<V>(key: (PropertyMetadata) -> String = propertyNameKeyProvider,
                                       default: (Any?, String) -> V = throwIfNoValue): ReadOnlyProperty<Any?, V> =
  KeyValueVal(bundleFromArguments, key, default, bundleContains, bundleDefaultRead)

/**
 * Delegates to a value in a bundle.
 *
 * @param bundle The bundle the value is in.
 * @param key Called to retrieve the Bundle key. By default this is the property name.
 * @param default The default value. By default throws NoDefaultValueException.
 */
public fun bundleVal<V>(bundle: Bundle,
                        key: (PropertyMetadata) -> String = propertyNameKeyProvider,
                        default: (Any?, String) -> V = throwIfNoValue): ReadOnlyProperty<Any?, V> =
    KeyValueVal({bundle}, key, default, bundleContains, bundleDefaultRead)

/**
 * Delegates to a variable in a bundle.
 *
 * Bundle variables support the following types:
 *  - String
 *  - Short
 *  - Int
 *  - Long
 *  - Float
 *  - Boolean
 *  - Byte
 *  - Char
 *  - CharSequence
 *  - Parcelable
 *  - Serializable
 *
 * Using this with an unsupported type will result in an UnsupportedTypeException being thrown when
 * set is called.
 *
 * @param The bundle the value is in.
 * @param key Called to retrieve the Bundle key. By default this is the property name.
 * @param default The default value. By default throws NoDefaultValueException.
 */
public fun bundleVar<V>(bundle: Bundle,
                        key: (PropertyMetadata) -> String = propertyNameKeyProvider,
                        default: (Any?, String) -> V = throwIfNoValue): ReadWriteProperty<Any?, V> =
    KeyValueVar({bundle}, key, default, bundleContains, bundleDefaultRead, bundleDefaultPut)

private val bundleContains:(Bundle, String) -> Boolean = {(b, k) -> b.containsKey(k)}
private val bundleDefaultRead:(Bundle, String) -> Any = {(bundle, key) -> bundle.get(key)}
private val bundleFromArguments:(Any?) -> Bundle =
    {(thisRef) ->
      when (thisRef) {
        is Fragment -> thisRef.getArguments()
        is SupportFragment -> thisRef.getArguments()
        else -> throw UnsupportedTypeException()
      }
    }
private val bundleDefaultPut:(Bundle, String, Any) -> Unit = {(bundle, key, value) ->
  when (value) {
    is String -> bundle.putString(key, value)
    is Short -> bundle.putShort(key, value)
    is Int -> bundle.putInt(key, value)
    is Long -> bundle.putLong(key, value)
    is Float -> bundle.putFloat(key, value)
    is Boolean -> bundle.putBoolean(key, value)
    is Byte -> bundle.putByte(key, value)
    is Char -> bundle.putChar(key, value)
    is CharSequence -> bundle.putCharSequence(key, value)
    is Parcelable -> bundle.putParcelable(key, value)
    is Serializable -> bundle.putSerializable(key, value)
    else -> throw UnsupportedTypeException()
  }
}

