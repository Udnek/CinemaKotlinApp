package me.udnekjupiter.cinemaapp.data

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class KinopoiskApi(private val context: Context) {

    companion object{
        const val BASE_URL = "https://kinopoiskapiunofficial.tech"
        private const val KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    }

    fun get(url: String, headerModifier: (MutableMap<String, String>) -> Unit = {}, listener: (JsonObject) -> Unit, errorListener: ErrorListener? = null) {
        val queue = Volley.newRequestQueue(context)
        val request = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            {json -> listener(JsonParser.parseString(json.toString()).asJsonObject)},
            errorListener
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val map = HashMap<String, String>()
                map["X-API-KEY"] = KEY
                map["Content-Type"] = "application/json"
                headerModifier(map)
                return map
            }
        }
        queue.add(request)
    }


    fun getTop100(page: Int = 1, listener: (List<Film>?) -> Unit){
        val type: String = "TOP_250_MOVIES"
        get(
            "$BASE_URL/api/v2.2/films/collections?type=$type&page=$page",
            listener = {json -> listener(json.get("items").asJsonArray.map { element -> Film(element.asJsonObject) })},
            errorListener =  {listener(null)}
        )
    }

    fun getExtraData(film: Film, listener: (JsonObject?) -> Unit){
        get(
            "$BASE_URL/api/v2.2/films/${film.getId()}",
            listener = {json -> listener(json)},
            errorListener = {listener(null)}
        )
    }
}











