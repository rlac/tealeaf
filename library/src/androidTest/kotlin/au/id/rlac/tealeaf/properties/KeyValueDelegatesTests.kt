package au.id.rlac.tealeaf.properties

import android.support.test.runner.AndroidJUnit4
import java.util.HashMap
import org.junit.Test
import org.junit.runner.RunWith

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown

RunWith(javaClass<AndroidJUnit4>())
public class KeyValueDelegatesTests {
  class object {
    private val readMap: HashMap<String, String>.(String) -> String = {(k) -> get(k) }
    private val writeMap: HashMap<String, String>.(String, String) -> Unit = {(k, v) -> put(k, v) }
    private val contains: HashMap<String, String>.(String) -> Boolean = {(k) -> containsKey(k) }
  }

  /** Test an exception is thrown by throwIfNoValue when the dictionary does not contain the key. */
  Test
  public fun testThrowWhenNoValue() {
    class Test(map: (Any) -> HashMap<String, String>) {
      val test by KeyValueVal(map, propertyNameKey, throwIfNoValue, contains, readMap)
    }

    val map = HashMap<String, String>()

    try {
      val test = Test({ map })
      test.test
      failBecauseExceptionWasNotThrown(javaClass<NoDefaultValueException>())
    } catch (e: Exception) {
      assertThat(e).isInstanceOf(javaClass<NoDefaultValueException>())
    }
  }

  /** Test a default value is returned when specified if the dictionary does not contain the key. */
  Test
  public fun testDefaultWhenNoValue() {
    class Test(map: (Any) -> HashMap<String, String>) {
      val test by KeyValueVal(map, propertyNameKey, {(ref, key): String -> "Default" }, contains, readMap)
    }

    val map = HashMap<String, String>()

    val test = Test({ map })
    assertThat(test.test).isEqualTo("Default")
  }

  /** Test the default value is not used when the dictionary contains the key. */
  Test
  public fun testNoDefaultWhenValue() {
    class Test(map: (Any) -> HashMap<String, String>) {
      val test by KeyValueVal(map, propertyNameKey, {(ref, key): String -> "Default" }, contains, readMap)
    }

    val map = HashMap<String, String>()
    map.put("test", "Test")

    val test = Test({ map })
    assertThat(test.test).isEqualTo("Test")
  }

  /** Test custom keys work. */
  Test
  public fun testCustomKey() {
    class Test(map: (Any) -> HashMap<String, String>) {
      val test by KeyValueVal(map, { "custom property name" }, throwIfNoValue, contains, readMap)
    }

    val map = HashMap<String, String>()
    map.put("custom property name", "test value")

    val test = Test({ map })
    assertThat(test.test).isEqualTo("test value")
  }

  /** Test the Cached implementation caches the value read the first time. */
  Test
  public fun testCachedVal() {
    class Test(map: HashMap<String, String>, default: (Any, String) -> String) {
      val test by CachedKeyValueVal({ map }, propertyNameKey, default, contains, readMap)
    }

    val map = HashMap<String, String>()
    var defaultAccessCount = 0
    val default: (Any, String) -> String = {(ref, key) -> defaultAccessCount += 1; "default" }

    val test = Test(map, default)
    assertThat(defaultAccessCount).isEqualTo(0)
    assertThat(test.test).isEqualTo("default")
    assertThat(defaultAccessCount).isEqualTo(1)
    assertThat(test.test).isEqualTo("default")
    assertThat(defaultAccessCount).isEqualTo(1)
  }
}
