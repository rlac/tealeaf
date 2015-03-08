package au.id.rlac.tealeaf.properties

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

private val propertyNameKeyProvider:(PropertyMetadata) -> String = { it.name }
private val throwIfNoValue:(Any?, Any?) -> Nothing = {(thisRef, key) -> throw NoDefaultValueException()}

public class NoDefaultValueException : Exception()
public class UnsupportedTypeException : Exception()

open class KeyValueVal<T, V, K, D>(val dict: (ref: T) -> D,
                                   val key: (PropertyMetadata) -> K,
                                   val default: (ref: T, key: K) -> V,
                                   val contains: (dict: D, key: K) -> Boolean,
                                   val read: (D, K) -> Any) :
    ReadOnlyProperty<T, V> {

  override fun get(thisRef: T, desc: PropertyMetadata): V {
    val key = key(desc)
    val dictionary = dict(thisRef)

    [suppress("UNCHECKED_CAST")]
    return if (contains(dictionary, key)) read(dictionary, key) as V
    else default(thisRef, key)
  }
}

open class KeyValueVar<T, V, K, D>(dict: (ref: T) -> D,
                                   key: (PropertyMetadata) -> K,
                                   default: (ref: T, key: K) -> V,
                                   contains: (dict: D, key: K) -> Boolean,
                                   read: (D, K) -> Any,
                                   val write: (D, K, V) -> Unit) :
    KeyValueVal<T, V, K, D>(dict, key, default, contains, read),
    ReadWriteProperty<T, V> {

  override fun set(thisRef: T, desc: PropertyMetadata, value: V) {
    write(dict(thisRef), key(desc), value)
  }
}