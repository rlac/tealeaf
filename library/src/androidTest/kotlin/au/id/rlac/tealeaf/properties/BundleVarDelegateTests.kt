package au.id.rlac.tealeaf.properties

import android.os.Bundle
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import org.junit.Test

import org.assertj.core.api.Assertions.assertThat
import org.assertj.android.api.Assertions.assertThat
import org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown

RunWith(javaClass<AndroidJUnit4>())
public class BundleVarDelegateTests {

  /**
   * Test that bundle values can be set using bundleVar and are keyed by variable name.
   */
  Test
  public fun testVariableWritable() {
    class Test(b: Bundle) {
      var short: Short by bundleVar(b)
      var int: Int by bundleVar(b)
      var long: Long by bundleVar(b)
      var float: Float by bundleVar(b)
      var string: String by bundleVar(b)
      var bool: Boolean by bundleVar(b)
      var byte: Byte by bundleVar(b)
      var char: Char by bundleVar(b)
    }

    val b = Bundle()
    val v = Test(b)

    v.int = 5
    v.short = 1
    v.long = 1000L
    v.float = 5.5F
    v.string = "String"
    v.bool = true
    v.byte = 1
    v.char = 'c'

    assertThat(b)
        .hasSize(8)
        .hasKey("short")
        .hasKey("int")
        .hasKey("long")
        .hasKey("float")
        .hasKey("string")
        .hasKey("bool")
        .hasKey("byte")
        .hasKey("char")

    assertThat(b.getShort("short")).isEqualTo(1)
    assertThat(b.getInt("int")).isEqualTo(5)
    assertThat(b.getLong("long")).isEqualTo(1000L)
    assertThat(b.getFloat("float")).isEqualTo(5.5F)
    assertThat(b.getString("string")).isEqualTo("String")
    assertThat(b.getBoolean("bool")).isTrue()
    assertThat(b.getByte("byte")).isEqualTo(1.toByte())
    assertThat(b.getChar("char")).isEqualTo('c')
  }

  /** Test custom keys can be specified. */
  Test
  public fun testAlternateKey() {
    class Test(b: Bundle) {
      var default: Int by bundleVar(b, key = { p1 -> "custom key"})
    }

    val b = Bundle()
    val v = Test(b)
    v.default = 5
    assertThat(b).hasSize(1).hasKey("custom key")
    assertThat(b.getInt("custom key")).isEqualTo(5)
  }
}