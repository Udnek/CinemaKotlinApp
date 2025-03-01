package me.udnekjupiter.cinemaapp.data

import java.io.Serializable

class SerializedFilm : Serializable{

    val mainData: String
    val extraData: String?

    constructor(film: Film){
        mainData = film.mainData.toString()
        extraData = film.extraData?.toString()
    }

    fun deserialize(): Film = Film(this)
}