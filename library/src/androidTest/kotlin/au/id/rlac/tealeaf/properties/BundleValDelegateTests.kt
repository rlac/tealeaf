package au.id.rlac.tealeaf.properties

import android.os.Bundle
import android.support.test.runner.AndroidJUnit4
import kotlin.properties.Delegates
import org.junit.runner.RunWith
import org.junit.Test
import org.junit.Before
import org.junit.After

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown

RunWith(javaClass<AndroidJUnit4>())
public class BundleValDelegateTests {

  /** Test exception is thrown when no default specified and no default set. */
  Test
  public fun testNoDefaultThrowsWhenNoValue() {
    class Test {
      val noDefault: Int by bundleVal(Bundle())
    }

    try {
      val v = Test()
      v.noDefault
      failBecauseExceptionWasNotThrown(javaClass<NoDefaultValueException>())
    } catch (e: Exception) {
      assertThat(e).isInstanceOf(javaClass<NoDefaultValueException>())
    }
  }

  /** Test default values work. */
  Test
  public fun testDefaultWhenNoValue() {
    class Test {
      val default: Int by bundleVal(Bundle(), default = { p1, p2 -> 1 })
    }

    val v = Test()
    assertThat(v.default).isEqualTo(1)
  }

  /** Test alternate keys can be specified. */
  Test
  public fun testCustomKey() {
    class Test(b: Bundle) {
      val customKey: Int by bundleVal(b, key = { p1 -> "custom key" })
    }

    val b = Bundle()
    b.putInt("custom key", 5)
    val v = Test(b)

    assertThat(v.customKey).isEqualTo(5)
  }

  /** Test values can be read from bundle. */
  Test
  public fun testValueReadable() {
    class Test(b: Bundle) {
      val preset: Int by bundleVal(b)
    }

    val bundle = Bundle()
    bundle.putInt("preset", 1)

    val v = Test(bundle)
    assertThat(v.preset).isEqualTo(1)
  }

}