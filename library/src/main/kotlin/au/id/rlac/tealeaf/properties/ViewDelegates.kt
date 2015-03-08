package au.id.rlac.tealeaf.properties

import android.app.Activity
import android.view.View
import kotlin.properties.ReadOnlyProperty
import android.view.ViewGroup

/**
 * Delegate property for views on Activity classes.
 *
 * class MyActivity : Activity() {
 *   val myTextView: TextView by viewById(R.id.my_text_view)
 * }
 *
 * @param id The id of the view to find.
 * @return a delegate property that finds the view in the Activity by id when first accessed.
 */
public fun Activity.viewById<T : View>(id: Int): ReadOnlyProperty<Any, T> = ViewVal(id)

/**
 * Delegate property for views with the [ViewHolder] trait.
 *
 * class MyViewHolder(val view: View) : ViewHolder {
 *   val myTextView: TextView by viewById(R.id.my_text_view)
 * }
 *
 * @param id The id of the view to find.
 * @return a delegate property that finds a child of the view property by id when first accessed.
 */
public fun ViewHolder.viewById<T : View>(id: Int): ReadOnlyProperty<Any, T> = ViewVal(id)

/**
 * Delegate property for views within a ViewGroup.
 *
 * class MyCustomViewGroup(c: Context, a: AttributeSet?) : FrameLayout(c, a, 0) {
 *   val myTextView: TextView by viewById(R.id.my_text_view)
 * }
 *
 * @param id The id of the view to find.
 * @return a delegate property that finds a view under the ViewGroup by id when first accessed
 */
public fun ViewGroup.viewById<T : View>(id: Int): ReadOnlyProperty<Any, T> = ViewVal(id)

public trait ViewHolder {
  val view: View
}

private class ViewVal<T : View>(val id: Int) : ReadOnlyProperty<Any, T> {
  var value: T? = null

  override fun get(thisRef: Any, desc: PropertyMetadata): T {
    [suppress("UNCHECKED_CAST")]
    if (value == null) {
      value = when (thisRef) {
        is Activity -> thisRef.findViewById(id)
        is ViewHolder -> thisRef.view.findViewById(id)
        is ViewGroup -> thisRef.findViewById(id)
        else -> throw IllegalStateException("Unsupported type.")
      } as T
    }

    return value!!
  }
}
