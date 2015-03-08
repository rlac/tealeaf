package au.id.rlac.tealeaf.collections

/**
 * Clamp to the range specified by the min and max values.
 * @param min Minimum value. Returned if this is less than min.
 * @param max Maximum value. Returned if this is greater than min.
 */
public fun <T : Comparable<T>> T.clamp(min: T, max: T): T =
    if (this < min) min else if (this > max) max else this
