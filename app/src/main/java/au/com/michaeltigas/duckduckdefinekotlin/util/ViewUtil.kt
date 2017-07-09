package au.com.michaeltigas.duckduckdefinekotlin.util

import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import au.com.michaeltigas.duckduckdefinekotlin.R


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Provide View-related utility methods to alter UI elements
 */
class ViewUtil private constructor() {
    companion object {
        /**
         * InArray with colour values, to theme the Swipe Refresh Layout palette
         */
        val colorPalleteSwipeRefresh: IntArray =
                intArrayOf(
                        R.color.swiperefreshcolor_one,
                        R.color.swiperefreshcolor_two,
                        R.color.swiperefreshcolor_three,
                        R.color.swiperefreshcolor_four)

        /**
         * Fix MATCH_PARENT bug of a layout within a list not stretching to its full width
         */
        fun setListLayoutBoundsMaxWidth(layoutView: View) {
            layoutView.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }
    }
}
