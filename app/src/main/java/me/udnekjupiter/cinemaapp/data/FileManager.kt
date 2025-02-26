package me.udnekjupiter.cinemaapp.data

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.activity.ComponentActivity.MODE_PRIVATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL

private const val PINNED_FILMS_FILE = "films.txt"
private const val PINNED_FILM_PREFIX = "film."
private const val POSTER_PREFIX = "poster."
private const val DATA_PREFIX = "data."

class FileManager(private val context: Context) {

    fun getPosterFileName(film: Film): String = "pinned_film/poster_${film.getId()}.jpg"
    fun getDataFileName(film: Film): String = "pinned_film/data_${film.getId()}.txt"

    fun loadFile(file: String): List<String> {
        return try {
            context.openFileInput(file).bufferedReader().readLines()
        } catch (e: Exception){
            ArrayList()
        }
    }
    fun loadImage(file: String): ImageView{
        val bitmap = BitmapFactory.decodeFile(file)
        val imageView = ImageView(context)
        imageView.setImageBitmap(bitmap)
        return imageView
    }

    fun saveFile(file: String, data: ByteArray){
        context.openFileOutput(file, MODE_PRIVATE).use {
                fileOutputStream -> fileOutputStream.write(data)
        }
    }
    fun saveFile(file: String, data: List<Any>){
        var strData = ""
        for (pinnedId in data) {
            strData += pinnedId.toString() + "\n"
        }
        saveFile(file, strData.toByteArray())
    }
    fun saveImage(file: String, url: String){
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val imageData: ByteArray = URL(url).readBytes()
            saveFile(file, imageData)
        }
    }

    fun savePinnedFilm(film: Film){
        ArrayList<String>()
        val data = loadFile(PINNED_FILMS_FILE)
        if (data.contains(film.getId().toString())) return
        val ids = ArrayList<Int>()
        ids.addAll(data.map { l -> l.toInt() })
        ids.add(film.getId())
        saveFile(PINNED_FILMS_FILE, ids)
    }



}









