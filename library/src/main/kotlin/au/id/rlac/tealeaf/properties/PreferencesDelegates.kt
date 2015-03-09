package au.id.rlac.tealeaf.properties

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.properties.ReadOnlyProperty

/**
 * Property delegates backed by a shared preferences value.
 */
public object PreferencesDelegates {
  public fun stringVal(sp: (Any) -> SharedPreferences,
                       key: (PropertyMetadata) -> String = propertyNameKey,
                       default: (Any?, String) -> String = throwIfNoValue,
                       contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadOnlyProperty<Any, String> =
      KeyValueVal(sp, key, default, contains, readString)

  public fun stringVar(sp: (Any) -> SharedPreferences,
                       key: (PropertyMetadata) -> String = propertyNameKey,
                       default: (Any?, String) -> String = throwIfNoValue,
                       contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadWriteProperty<Any, String> =
      KeyValueVar(sp, key, default, contains, readString, writeString)

  public fun booleanVal(sp: (Any) -> SharedPreferences,
                        key: (PropertyMetadata) -> String = propertyNameKey,
                        default: (Any?, String) -> Boolean = throwIfNoValue,
                        contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadOnlyProperty<Any, Boolean> =
      KeyValueVal(sp, key, default, contains, readBoolean)

  public fun booleanVar(sp: (Any) -> SharedPreferences,
                        key: (PropertyMetadata) -> String = propertyNameKey,
                        default: (Any?, String) -> Boolean = throwIfNoValue,
                        contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadWriteProperty<Any, Boolean> =
      KeyValueVar(sp, key, default, contains, readBoolean, writeBoolean)

  public fun intVal(sp: (Any) -> SharedPreferences,
                    key: (PropertyMetadata) -> String = propertyNameKey,
                    default: (Any?, String) -> Int = throwIfNoValue,
                    contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadOnlyProperty<Any, Int> =
      KeyValueVal(sp, key, default, contains, readInt)

  public fun intVar(sp: (Any) -> SharedPreferences,
                    key: (PropertyMetadata) -> String = propertyNameKey,
                    default: (Any?, String) -> Int = throwIfNoValue,
                    contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadWriteProperty<Any, Int> =
      KeyValueVar(sp, key, default, contains, readInt, writeInt)

  public fun floatVal(sp: (Any) -> SharedPreferences,
                      key: (PropertyMetadata) -> String = propertyNameKey,
                      default: (Any?, String) -> Float = throwIfNoValue,
                      contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadOnlyProperty<Any, Float> =
      KeyValueVal(sp, key, default, contains, readFloat)

  public fun floatVar(sp: (Any) -> SharedPreferences,
                      key: (PropertyMetadata) -> String = propertyNameKey,
                      default: (Any?, String) -> Float = throwIfNoValue,
                      contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadWriteProperty<Any, Float> =
      KeyValueVar(sp, key, default, contains, readFloat, writeFloat)

  public fun longVal(sp: (Any) -> SharedPreferences,
                     key: (PropertyMetadata) -> String = propertyNameKey,
                     default: (Any?, String) -> Long = throwIfNoValue,
                     contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadOnlyProperty<Any, Long> =
      KeyValueVal(sp, key, default, contains, readLong)

  public fun longVar(sp: (Any) -> SharedPreferences,
                     key: (PropertyMetadata) -> String = propertyNameKey,
                     default: (Any?, String) -> Long = throwIfNoValue,
                     contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadWriteProperty<Any, Long> =
      KeyValueVar(sp, key, default, contains, readLong, writeLong)

  public fun customVal<V>(sp: (Any) -> SharedPreferences,
                          read: SharedPreferences.(String) -> V,
                          key: (PropertyMetadata) -> String = propertyNameKey,
                          default: (Any?, String) -> V = throwIfNoValue,
                          contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadOnlyProperty<Any, V> =
      KeyValueVal(sp, key, default, contains, read)

  public fun customVal<V>(sp: (Any) -> SharedPreferences,
                          read: SharedPreferences.(String) -> V,
                          write: SharedPreferences.(String, V) -> Unit,
                          key: (PropertyMetadata) -> String = propertyNameKey,
                          default: (Any?, String) -> V = throwIfNoValue,
                          contains: SharedPreferences.(String) -> Boolean = PreferencesDelegates.contains): ReadWriteProperty<Any, V> =
      KeyValueVar(sp, key, default, contains, read, write)

  private val contains: SharedPreferences.(String) -> Boolean = {(k) -> contains(k) }
  private val readString: SharedPreferences.(String) -> String = {(k) -> getString(k, "") }
  private val writeString: SharedPreferences.(String, String) -> Unit = {(k, v) -> edit().putString(k, v).apply() }
  private val readInt: SharedPreferences.(String) -> Int = {(k) -> getInt(k, 0) }
  private val writeInt: SharedPreferences.(String, Int) -> Unit = {(k, v) -> edit().putInt(k, v).apply() }
  private val readBoolean: SharedPreferences.(String) -> Boolean = {(k) -> getBoolean(k, false) }
  private val writeBoolean: SharedPreferences.(String, Boolean) -> Unit = {(k, v) -> edit().putBoolean(k, v).apply() }
  private val readLong: SharedPreferences.(String) -> Long = {(k) -> getLong(k, 0L) }
  private val writeLong: SharedPreferences.(String, Long) -> Unit = {(k, v) -> edit().putLong(k, v).apply() }
  private val readFloat: SharedPreferences.(String) -> Float = {(k) -> getFloat(k, 0F) }
  private val writeFloat: SharedPreferences.(String, Float) -> Unit = {(k, v) -> edit().putFloat(k, v).apply() }
}
