package au.id.rlac.tealeaf.parcelable

import android.os.Parcel
import android.support.test.runner.AndroidJUnit4

import java.math.BigInteger
import java.math.BigDecimal

import org.junit.runner.RunWith
import org.junit.Test

import org.assertj.core.api.Assertions.assertThat

/**
 * Verify the Parcel extension methods serialize and deserialize correctly.
 */
RunWith(javaClass<AndroidJUnit4>())
public class ParcelExtensionsTests {

  Test
  public fun testNonNullNullableBigInteger() {
    val p = Parcel.obtain()

    try {
      val b: BigInteger? = BigInteger("5")
      p.writeNullableBigInteger(b)
      p.setDataPosition(0)

      assertThat(p.readNullableBigInteger()).isEqualTo(b)
    } finally {
      p.recycle()
    }
  }

  Test
  public fun testNullBigInteger() {
    val p = Parcel.obtain()

    try {
      p.writeNullableBigInteger(null)
      p.setDataPosition(0)

      assertThat(p.readNullableBigInteger()).isNull()
    } finally {
      p.recycle()
    }
  }

  Test
  public fun testBigInteger() {
    val p = Parcel.obtain()

    try {
      val b = BigInteger("6")
      p.writeBigInteger(b)
      p.setDataPosition(0)

      assertThat(p.readBigInteger()).isEqualTo(b)
    } finally {
      p.recycle()
    }
  }

  Test
  public fun testNonNullNullableBoolean() {
    val p = Parcel.obtain()

    try {
      val b1: Boolean? = true
      val b2: Boolean? = false
      p.writeNullableBoolean(b1)
      p.writeNullableBoolean(b2)
      p.setDataPosition(0)

      assertThat(p.readNullableBoolean()).isEqualTo(true)
      assertThat(p.readNullableBoolean()).isEqualTo(false)
    } finally {
      p.recycle()
    }
  }

  Test
  public fun testNullBoolean() {
    val p = Parcel.obtain()

    try {
      p.writeNullableBoolean(null)
      p.setDataPosition(0)

      assertThat(p.readNullableBoolean()).isNull()
    } finally {
      p.recycle()
    }
  }

  Test
  public fun testBoolean() {
    val p = Parcel.obtain()

    try {
      val b1 = true
      val b2 = false
      p.writeBoolean(b1)
      p.writeBoolean(b2)
      p.setDataPosition(0)

      assertThat(p.readBoolean()).isTrue()
      assertThat(p.readBoolean()).isEqualTo(false)
    } finally {
      p.recycle()
    }

  }

  Test
  public fun testBigDecimal() {
    val p = Parcel.obtain()

    try {
      val bd = BigDecimal("5.2")
      p.writeBigDecimal(bd)
      p.setDataPosition(0)

      assertThat(p.readBigDecimal()).isEqualTo(bd)
    } finally {
      p.recycle()
    }
  }

  Test
  public fun testNonNullNullableBigDecimal() {
    val p = Parcel.obtain()

    try {
      val bd: BigDecimal? = BigDecimal("5.3")
      p.writeNullableBigDecimal(bd)
      p.setDataPosition(0)

      assertThat(p.readNullableBigDecimal()).isEqualTo(bd)
    } finally {
      p.recycle()
    }
  }

  Test
  public fun testNullBigDecimal() {
    val p = Parcel.obtain()

    try {
      p.writeNullableBigDecimal(null)
      p.setDataPosition(0)

      assertThat(p.readNullableBigDecimal()).isNull()
    } finally {
      p.recycle()
    }
  }
}