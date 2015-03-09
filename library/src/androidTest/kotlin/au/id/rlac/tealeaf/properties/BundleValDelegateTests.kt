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

  /** Test values can be read from bundle. */
  Test
  public fun testValueReadable() {
    class Test(b: Bundle) {
      val preset: Int by BundleDelegates.bundleVal(b)
    }

    val bundle = Bundle()
    bundle.putInt("preset", 1)

    val v = Test(bundle)
    assertThat(v.preset).isEqualTo(1)
  }

  /** Test the value mapping function is used. */
  Test
  public fun testMappedValue() {
    [data] class Custom(val i: Int)

    class Test(b: Bundle) {
      val custom: Custom by BundleDelegates.mapBundleVal({b}, map = {
        (i: Int) -> Custom(i)
      })
    }

    val b = Bundle()
    b.putInt("custom", 5)

    val v = Test(b)
    assertThat(v.custom).isEqualTo(Custom(5))
  }
}