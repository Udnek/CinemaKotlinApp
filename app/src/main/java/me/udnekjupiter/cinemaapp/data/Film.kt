package me.udnekjupiter.cinemaapp.data


import com.google.android.gms.common.internal.Preconditions
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.udnekjupiter.cinemaapp.data.KinopoiskApi
import java.net.URI
import java.net.URL


class Film {

    // TODO: Add bool function to get if film is favorited
    // TODO: Add function to add film to favorites

    private val mainData: JsonObject
    private var extraData : JsonObject? = null
    var triedLoadingExtraData = false
        protected set

    constructor(mainData: JsonObject){
        this.mainData = mainData
    }

    private fun getData(name: String, json: JsonObject = mainData): JsonElement {
        val element = json.get(name)
        assert(element != null)
        return element;
    }

    fun getId(): Int = getData("filmId").asInt
    fun getRuName(): String = getData("nameRu").asString
    fun getPosterUrl(): URL = URI.create(getData("posterUrl").asString).toURL()
    fun getYear(): Int = getData("year").asInt
    fun getGenres(): List<String> {
        return getData("genres").asJsonArray.map { element -> element.asString }
    }
    fun getCountries(): List<String> {
        return getData("countries").asJsonArray.map { element -> element.asString }
    }
    fun loadExtraData(api: KinopoiskApi, listener: (Film) -> Unit){
        api.getExtraData(this) {
            json ->
                extraData = json
                triedLoadingExtraData = true
                listener(this)
        }
    }

    fun getLoadedDescription(): String? {
        Preconditions.checkArgument(triedLoadingExtraData, "Extra description was not loaded!")
        val data = extraData
        return data?.let {getData("description", data).asString }
    }
}












