package au.id.rlac.tealeaf.util

import java.io.InputStream
import java.io.OutputStream
import android.database.Cursor
import android.graphics.Bitmap
import android.content.res.TypedArray
import android.os.Parcel

/* Functions to reduce boilerplate to recycle objects with a recycle method after use. */

public inline fun <R> Bitmap.use(block: (Bitmap) -> R): R =
    try { block(this) }
    finally { recycle() }

public inline fun <R> TypedArray.use(block: (TypedArray) -> R): R =
    try { block(this) }
    finally { recycle() }

public inline fun <R> Parcel.use(block: (Parcel) -> R): R =
    try { block(this) }
    finally { recycle() }
