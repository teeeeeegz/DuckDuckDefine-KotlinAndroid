package au.com.michaeltigas.duckduckdefinekotlin.data.remote

import au.com.michaeltigas.duckduckdefinekotlin.data.remote.model.SearchDefinition
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * All DuckDuckGo API endpoints go in this class
 */
interface DuckDuckGoApiService {
    @GET(".")
    fun searchRequest(@QueryMap parameters: Map<String, String>): Single<SearchDefinition>
}
