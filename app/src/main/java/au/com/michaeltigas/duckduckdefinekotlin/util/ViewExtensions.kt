package au.com.michaeltigas.duckduckdefinekotlin.util

import android.support.v4.widget.SwipeRefreshLayout


/**
 * Created by Michael Tigas on 9/7/17.
 *
 * Provide extensions for [View] related classes
 */

/**
 * Enable/Disable refreshing state of SwipeRefreshLayout
 */
fun SwipeRefreshLayout.setIsRefreshing(setRefresh: Boolean) {
    if (setRefresh) {
        if (!isRefreshing) isRefreshing = true
    } else {
        if (isRefreshing) isRefreshing = false
    }
}
