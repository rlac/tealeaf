package au.id.rlac.tealeaf.util

import java.io.InputStream
import java.io.OutputStream
import android.database.Cursor
import android.graphics.Bitmap
import android.content.res.TypedArray
import android.os.Parcel

/* Functions to reduce boilerplate when cleaning up using disposable or pooled objects after use. */

public inline fun <R> using(inputStream: InputStream, f: InputStream.() -> R): R =
    try { inputStream.f() }
    finally { inputStream.close() }

public inline fun <R> using(outputStream: OutputStream, f: OutputStream.() -> R): R =
    try { outputStream.f() }
    finally { outputStream.close() }

public inline fun <R> using(cursor: Cursor, f: Cursor.() -> R): R =
    try { cursor.f() }
    finally { cursor.close() }

public inline fun <R> using(bitmap: Bitmap, f: Bitmap.() -> R): R =
    try { bitmap.f() }
    finally { bitmap.recycle() }

public inline fun <R> using(typedArray: TypedArray, f: TypedArray.() -> R): R =
    try { typedArray.f() }
    finally { typedArray.recycle() }

public inline fun <R> using(parcel: Parcel, f: Parcel.() -> R): R =
    try { parcel.f() }
    finally { parcel.recycle() }
