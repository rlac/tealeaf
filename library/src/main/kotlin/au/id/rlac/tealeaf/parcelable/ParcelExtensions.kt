package au.id.rlac.tealeaf.parcelable

import android.os.Parcel

import java.math.BigInteger
import java.math.BigDecimal

/* Extension methods to add read and write methods for additional types to Parcel. */

public fun Parcel.writeBigInteger(bi: BigInteger) {
  with (bi.toByteArray()) {
    writeInt(size())
    writeByteArray(this)
  }
}

public fun Parcel.readBigInteger(): BigInteger =
    with (ByteArray(readInt())) {
      readByteArray(this)
      BigInteger(this)
    }

public fun Parcel.writeNullableBigInteger(bi: BigInteger?) {
  if (bi == null) writeByte(0)
  else {
    writeByte(1)
    writeBigInteger(bi)
  }
}

public fun Parcel.readNullableBigInteger(): BigInteger? =
    if (readByte() == 0.toByte()) null
    else readBigInteger()

public fun Parcel.writeBigDecimal(bd: BigDecimal) {
  writeBigInteger(bd.unscaledValue())
  writeInt(bd.scale())
}

public fun Parcel.readBigDecimal(): BigDecimal = BigDecimal(readBigInteger(), readInt())

public fun Parcel.writeNullableBigDecimal(bd: BigDecimal?) {
  if (bd == null) writeByte(0)
  else {
    writeByte(1)
    writeBigDecimal(bd)
  }
}

public fun Parcel.readNullableBigDecimal(): BigDecimal? =
    if (readByte() == 0.toByte()) null
    else readBigDecimal()

public fun Parcel.writeBoolean(b: Boolean) {
  writeByte(if (b) 1 else 0)
}

public fun Parcel.readBoolean(): Boolean = readByte() == 1.toByte()

public fun Parcel.writeNullableBoolean(b: Boolean?) {
  if (b == null) writeByte(2)
  else writeBoolean(b)
}

public fun Parcel.readNullableBoolean(): Boolean? =
    when (readByte()) {
      0.toByte() -> false
      1.toByte() -> true
      else -> null
    }