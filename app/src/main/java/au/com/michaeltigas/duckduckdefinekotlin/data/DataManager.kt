package au.com.michaeltigas.duckduckdefinekotlin.data

import au.com.michaeltigas.duckduckdefinekotlin.data.local.PreferencesHelper
import au.com.michaeltigas.duckduckdefinekotlin.data.remote.DuckDuckGoApiService
import au.com.michaeltigas.duckduckdefinekotlin.data.remote.model.SearchDefinition
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Responsible for facilitating communication between business logic (such as Network, Storage, etc.),
 * and Presenter classes
 */
@Singleton
class DataManager @Inject constructor(val duckDuckGoApiService: DuckDuckGoApiService, val preferencesHelper: PreferencesHelper) {

    //////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////  ========== PreferencesHelper ONLY METHODS ========== ///////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Return the last search term
     */
    fun getLastSearchTerm() = preferencesHelper.getLastSearch()

    /**
     * Update the last search term
     */
    fun setLastSearchTerm(term: String) = preferencesHelper.setLastSearch(term)

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////  ========== DuckDuckGoApiService ONLY METHODS ========== //////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Send a search term request to get a SearchDefinition response
     */
    fun getSearchResults(searchTerm: String): Single<SearchDefinition> {
        val parameters = HashMap<String, String>().apply {
            put("q", searchTerm)
            put("format", "json")
            put("pretty", "1")
            put("no_html", "1")
            put("skip_disambig", "1")
        }

        return duckDuckGoApiService.searchRequest(parameters)
    }
}
