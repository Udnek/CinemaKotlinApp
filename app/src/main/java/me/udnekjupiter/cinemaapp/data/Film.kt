package me.udnekjupiter.cinemaapp.data

import android.util.Log
import android.widget.ImageView
import com.google.android.gms.common.internal.Preconditions
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import me.udnekjupiter.cinemaapp.MainActivity
import java.net.URI
import java.net.URL

class Film {
    val mainData: JsonObject
    var extraData : JsonObject?
        private set
    var triedLoadingExtraData: Boolean
        private set

    constructor(mainData: JsonObject, extraData: JsonObject? = null) {
        this.mainData = mainData
        this.extraData = extraData
        triedLoadingExtraData = extraData != null
    }

    constructor(serializedFilm: SerializedFilm) : this (
        JsonParser.parseString(serializedFilm.mainData).asJsonObject,
        serializedFilm.extraData?.let{ data -> JsonParser.parseString(data).asJsonObject }
    )

    companion object{
        fun loadAllPinned(): MutableList<Film> {
            val films: MutableList<Film> = ArrayList()
            for (id in MainActivity.fileManager.getPinnedFile()) {
                when (val loadedFilm = MainActivity.fileManager.loadFilm(id)) {
                    null -> Log.e("Film", "Can not load film with id: $id")
                    else -> films.add(loadedFilm)
                }
            }
            return films
        }
    }

    fun serialize(): SerializedFilm = SerializedFilm(this)

    private fun getData(name: String, nullJsonCase: JsonElement? = null, json: JsonObject = mainData): JsonElement {
        val element = json.get(name)
        Preconditions.checkArgument(element != null, "No '$name' in '$json'")
        if (element is JsonNull){
            return nullJsonCase ?: throw RuntimeException("'$name' is nullJson and nullJsonCase is null! json: '$json'")
        }
        return element
    }

    fun getId(): Int = getData("kinopoiskId").asInt
    fun getRuName(): String = getData("nameRu", JsonPrimitive("No ru name(((")).asString
    fun getPosterUrl(): URL = URI.create(getData("posterUrl").asString).toURL()
    fun getYear(): Int = getData("year", JsonPrimitive(-1)).asInt
    fun getGenres(): List<String> {
        return getData("genres").asJsonArray.map { element -> element.asJsonObject.get("genre").asString }
    }
    fun getCountries(): List<String> {
        return getData("countries").asJsonArray.map { element -> element.asJsonObject.get("country").asString }
    }
    fun loadExtraData(listener: (Film) -> Unit){
        MainActivity.api.getExtraData(this) {
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
    fun getSavedPoster(): ImageView? {
        val manager = MainActivity.fileManager
        return manager.loadImage(manager.getPosterFileName(this))
    }

    fun pin() = MainActivity.fileManager.pinFilm(this)
    fun isPinned() = MainActivity.fileManager.getPinnedFile().contains(getId())
    fun unpin() = MainActivity.fileManager.unpinFilm(this)
}












