package au.id.rlac.tealeaf.parcelable

import android.os.Parcelable
import android.os.Parcel

import kotlin.InlineOption.ONLY_LOCAL_RETURN

/**
 * Creates a Parcelable.Creator, reducing boilerplate code when implementing the CREATOR field of
 * a Parcelable.
 *
 * data class MyParcelable(var someProperty: String) : Parcelable {
 *   override fun describeContents(): Int = 0
 *   override fun writeToParcel(dest: Parcel, flags: Int) {
 *     dest.writeString(someProperty)
 *   }
 *
 *   class object {
 *     platformStatic val CREATOR = creator { MyParcelable(it.readString() }
 *   }
 * }
 */
public inline fun creator<reified T : Parcelable>(
    inlineOptions(ONLY_LOCAL_RETURN) createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
    object : Parcelable.Creator<T> {
      override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
      override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
    }
