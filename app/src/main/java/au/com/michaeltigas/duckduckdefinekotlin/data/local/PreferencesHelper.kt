package au.com.michaeltigas.duckduckdefinekotlin.data.local

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import au.com.michaeltigas.duckduckdefinekotlin.injection.module.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Access instance of SharedPreferences to get and update keys
 */
@Singleton
class PreferencesHelper @Inject constructor(@ApplicationContext val context: Context) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////  ========== PREFERENCE KEYS ========== ///////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    object Keys {
        const val LAST_SEARCH: String = "KEY_LAST_SEARCH"
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////  ========== PREFERENCE ACCESSOR METHODS ========== /////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return last search value
     */
    fun getLastSearch() = sharedPreferences.getString(Keys.LAST_SEARCH, "")

    /**
     * Save last search value
     */
    fun setLastSearch(term: String) = sharedPreferences.edit().putString(Keys.LAST_SEARCH, term).apply()
}
