package au.id.rlac.tealeaf.properties

import android.support.test.runner.AndroidJUnit4
import android.content.SharedPreferences
import android.support.test.InstrumentationRegistry
import android.content.Context
import org.junit.runner.RunWith
import org.junit.Test
import org.junit.Before
import org.junit.After

import org.assertj.core.api.Assertions.assertThat
import org.assertj.android.api.Assertions.assertThat

RunWith(javaClass<AndroidJUnit4>())
public class PreferencesDelegatesTests {
  class object {
    private val prefs: (Any?) -> SharedPreferences = {
      InstrumentationRegistry.getContext()
          .getSharedPreferences("preferences_delegates_test", Context.MODE_PRIVATE)
    }
  }

  private fun obtainSharedPrefs() = prefs(null)

  Before
  public fun setUp() {
    obtainSharedPrefs().edit().clear().commit()
  }

  After
  public fun tearDown() {
    obtainSharedPrefs().edit().clear().commit()
  }

  /** Test Long properties are persisted to shared preferences. */
  Test
  public fun testLongPreference() {
    class Test {
      var longPref by PreferencesDelegates.longVar(prefs, default = {(p1, p2) -> 5L })
    }

    val prefs = obtainSharedPrefs()
    val v = Test()

    assertThat(v.longPref).isEqualTo(5L)
    assertThat(prefs).doesNotHaveKey("longPref")
    v.longPref = 10L
    assertThat(v.longPref).isEqualTo(10L)
    assertThat(prefs).contains("longPref", 10L)
  }

  /** Test String properties are persisted to shared preferences. */
  Test
  public fun testStringPreference() {
    class Test {
      var stringPref by PreferencesDelegates.stringVar(prefs, default = {(p1, p2) -> "" })
    }

    val prefs = obtainSharedPrefs()
    val v = Test()

    assertThat(v.stringPref).isEqualTo("")
    assertThat(prefs).doesNotHaveKey("stringPref")
    v.stringPref = "testing"
    assertThat(v.stringPref).isEqualTo("testing")
    assertThat(prefs).contains("stringPref", "testing")
  }

  /** Test Boolean properties are persisted to shared preferences. */
  Test
  public fun testBooleanPreference() {
    class Test {
      var booleanPref by PreferencesDelegates.booleanVar(prefs, default = {(p1, p2) -> true })
    }

    val prefs = obtainSharedPrefs()
    val v = Test()

    assertThat(v.booleanPref).isTrue()
    assertThat(prefs).doesNotHaveKey("booleanPref")
    v.booleanPref = false
    assertThat(v.booleanPref).isFalse()
    assertThat(prefs).contains("booleanPref", false)
  }

  /** Test Float properties are persisted to shared preferences. */
  Test
  public fun testFloatPreference() {
    class Test {
      var floatPref by PreferencesDelegates.floatVar(prefs, default = {(p1, p2) -> 1F })
    }

    val prefs = obtainSharedPrefs()
    val v = Test()

    assertThat(v.floatPref).isEqualTo(1F)
    assertThat(prefs).doesNotHaveKey("floatPref")
    v.floatPref = 2F
    assertThat(v.floatPref).isEqualTo(2F)
    assertThat(prefs).contains("floatPref", 2F)
  }

  /** Test custom read and write is called for custom preference value serialization. */
  Test
  public fun testCustomPreference() {
    [data] class Custom(val str: String)

    class Test {
      var custom by PreferencesDelegates.customVal(
          prefs,
          read = {(key) -> Custom(getString(key, "")) },
          write = {(key, value) -> edit().putString(key, value.str).commit() },
          default = {(thisRef, key) -> Custom("testing") })
    }

    val prefs = obtainSharedPrefs()
    val v = Test()

    assertThat(v.custom).isEqualTo(Custom("testing"))
    assertThat(prefs).doesNotHaveKey("custom")
    v.custom = Custom("value")
    assertThat(v.custom).isEqualTo(Custom("value"))
    assertThat(prefs).contains("custom", "value")
  }
}