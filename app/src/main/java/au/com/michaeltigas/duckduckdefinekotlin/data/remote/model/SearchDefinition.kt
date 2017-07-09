package au.com.michaeltigas.duckduckdefinekotlin.data.remote.model

import com.squareup.moshi.Json


/**
 * Created by Michael Tigas on 7/7/17.
 *
 * Represents a SearchDefinition object
 */
data class SearchDefinition(@field:Json(name = "Heading") val heading: String,
                            @field:Json(name = "AbstractText") val abstractText: String,
                            @field:Json(name = "Type") val type: String,
                            @field:Json(name = "Image") val imageUrl: String)
