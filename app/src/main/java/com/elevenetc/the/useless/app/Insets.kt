package com.elevenetc.the.useless.app

import android.os.Parcelable


class Insets private constructor(val left: Int, val top: Int, val right: Int, val bottom: Int){
    /**
     * Returns a Rect instance with the appropriate values.
     *
     * @hide
     */
    fun toRect(): Rect {
        return Rect(left, top, right, bottom)
    }

    /**
     * Two Insets instances are equal iff they belong to the same class and their fields are
     * pairwise equal.
     *
     * @param o the object to compare this instance with.
     *
     * @return true iff this object is equal `o`
     */
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val insets = o as Insets
        if (bottom != insets.bottom) return false
        if (left != insets.left) return false
        if (right != insets.right) return false
        return if (top != insets.top) false else true
    }

    override fun hashCode(): Int {
        var result = left
        result = 31 * result + top
        result = 31 * result + right
        result = 31 * result + bottom
        return result
    }

    override fun toString(): String {
        return "Insets{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}'
    }

    companion object {
        val NONE = Insets(0, 0, 0, 0)
        // Factory methods
        /**
         * Return an Insets instance with the appropriate values.
         *
         * @param left the left inset
         * @param top the top inset
         * @param right the right inset
         * @param bottom the bottom inset
         *
         * @return Insets instance with the appropriate values
         */
        fun of(left: Int, top: Int, right: Int, bottom: Int): Insets {
            return if (left == 0 && top == 0 && right == 0 && bottom == 0) {
                NONE
            } else Insets(left, top, right, bottom)
        }

        /**
         * Return an Insets instance with the appropriate values.
         *
         * @param r the rectangle from which to take the values
         *
         * @return an Insets instance with the appropriate values
         */
        fun of( r: Rect?): Insets {
            return if (r == null) NONE else of(r.left, r.top, r.right, r.bottom)
        }

        /**
         * Add two Insets.
         *
         * @param a The first Insets to add.
         * @param b The second Insets to add.
         * @return a + b, i. e. all insets on every side are added together.
         */
        fun add(a: Insets, b: Insets): Insets {
            return of(a.left + b.left, a.top + b.top, a.right + b.right, a.bottom + b.bottom)
        }

        /**
         * Subtract two Insets.
         *
         * @param a The minuend.
         * @param b The subtrahend.
         * @return a - b, i. e. all insets on every side are subtracted from each other.
         */
        fun subtract(a: Insets, b: Insets): Insets {
            return of(a.left - b.left, a.top - b.top, a.right - b.right, a.bottom - b.bottom)
        }

        /**
         * Retrieves the maximum of two Insets.
         *
         * @param a The first Insets.
         * @param b The second Insets.
         * @return max(a, b), i. e. the larger of every inset on every side is taken for the result.
         */
        fun max(a: Insets, b: Insets): Insets {
            return of(
                Math.max(a.left, b.left), Math.max(a.top, b.top),
                Math.max(a.right, b.right), Math.max(a.bottom, b.bottom)
            )
        }

        /**
         * Retrieves the minimum of two Insets.
         *
         * @param a The first Insets.
         * @param b The second Insets.
         * @return min(a, b), i. e. the smaller of every inset on every side is taken for the result.
         */
        fun min(a: Insets, b: Insets): Insets {
            return of(
                Math.min(a.left, b.left), Math.min(a.top, b.top),
                Math.min(a.right, b.right), Math.min(a.bottom, b.bottom)
            )
        }
    }

}