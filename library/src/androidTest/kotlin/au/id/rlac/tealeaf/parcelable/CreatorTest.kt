package au.id.rlac.tealeaf.parcelable

import kotlin.platform.platformStatic

import android.os.Parcelable
import android.os.Parcel
import android.support.test.runner.AndroidJUnit4

import org.junit.runner.RunWith
import org.junit.Test

import org.assertj.core.api.Assertions.assertThat

/**
 * Verifies that the creator utility function creates a valid Parcelable.Creator.
 */
RunWith(javaClass<AndroidJUnit4>())
public class CreatorTest {

  Test
  public fun testCreator() {
    val p = Parcel.obtain()
    try {
      val original = TestParcelable(1, 2L)
      p.writeParcelable(original, 0)
      p.setDataPosition(0)
      val deserialized = p.readParcelable<TestParcelable>(javaClass<TestParcelable>().getClassLoader())

      assertThat(deserialized).isEqualTo(original)

    } finally {
      p.recycle()
    }
  }

  data class TestParcelable(val i1: Int, val l1: Long) : Parcelable  {
    override fun writeToParcel(dest: Parcel, flags: Int) {
      dest.writeInt(i1)
      dest.writeLong(l1)
    }

    override fun describeContents(): Int = 0

    class object {
      platformStatic val CREATOR = creator {
        TestParcelable(it.readInt(), it.readLong())
      }
    }
  }
}