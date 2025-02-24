package org.example

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import java.net.URI

class KinopoiskApi(private val context: Context) {

    companion object{
        const val BASE_URL = "https://kinopoiskapiunofficial.tech"
        private const val KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    }

    fun get(url: String, modifier: (JsonObject) -> Unit = {}, listener: (JsonObject) -> Unit, errorListener: ErrorListener? = null) {
        val queue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            JSONObject(
                JsonParser.parseString("{\"X-API-KEY\": \"$KEY\", \"Content-Type\", \"application/json\"}")
                    .asJsonObject.also(modifier).toString()
            ),
            {json -> listener(JsonParser.parseString(json.toString()).asJsonObject)},
            errorListener
        )
        queue.add(request)
    }

    fun getTop100(listener: (List<Film>?) -> Unit){
        get(
            "$BASE_URL/api/v2.2/films/top",
            {jsonObject -> jsonObject.addProperty("type", "TOP_100_POPULAR_FILMS")},
            {json -> listener(json.get("films").asJsonArray.map { element -> Film(element.asJsonObject) })},
            {listener(null)}
        )
    }

    fun getExtraData(film: Film, listener: (JsonObject?) -> Unit){
        get(
            "$BASE_URL/api/v2.2/films/${film.getId()}",
            {json -> listener(json)},
            {listener(null)})
    }


}











