package me.udnekjupiter.cinemaapp.data

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL


private const val PINNED_FILMS_FILE = "films.txt"

class FileManager(private val context: Context) {


    fun getPosterFileName(film: Film): String = "poster_${film.getId()}.jpg"
    fun getDataFileName(film: Film): String = getDataFileName(film.getId())
    fun getDataFileName(filmId: Int): String = "data_$filmId.txt"
    fun getExtraDataFileName(film: Film): String = getExtraDataFileName(film.getId())
    fun getExtraDataFileName(filmId: Int): String = "extra_data_$filmId.txt"

    // DELETING
    fun deleteFile(file: String){
        File(context.getFilesDir(), file).delete();
    }

    // LOADING
    fun loadFile(file: String): List<String> {
        return try {
            context.openFileInput(file).bufferedReader().readLines()
        } catch (e: Exception){
            ArrayList()
        }
    }
    fun loadImage(file: String): ImageView? {
        val bitmap = BitmapFactory.decodeFile(file) ?: return null
        val imageView = ImageView(context)
        imageView.setImageBitmap(bitmap)
        return imageView
    }
    // SAVING
    fun saveFile(file: String, data: ByteArray){
        context.openFileOutput(file, Context.MODE_PRIVATE).use {
                fileOutputStream -> fileOutputStream.write(data)
        }
    }
    fun saveFile(file: String, data: Iterable<Any>){
        var strData = ""
        for (pinnedId in data) {
            strData += pinnedId.toString() + "\n"
        }
        saveFile(file, strData.toByteArray())
    }
    fun saveImage(file: String, url: URL){
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val imageData: ByteArray = url.readBytes()
            saveFile(file, imageData)
        }
    }

    fun getPinnedFile(): HashSet<Int> {
        val data = loadFile(PINNED_FILMS_FILE)
        val ids = HashSet<Int>()
        ids.addAll(data.map { l -> l.toInt() })
        return ids;
    }
    fun editPinnedFile(modifier: (MutableSet<Int>) -> Unit){
        saveFile(PINNED_FILMS_FILE, getPinnedFile().also(modifier))
    }

    fun pinFilm(film: Film){
        editPinnedFile { ids -> ids.add(film.getId()) }
        saveFile(getDataFileName(film), listOf(film.mainData.toString()))
        saveImage(getPosterFileName(film), film.getPosterUrl())
        film.loadExtraData { _ -> saveFile(getExtraDataFileName(film), film.extraData.toString().toByteArray()) }
    }
    fun unpinFilm(film: Film){
        editPinnedFile { ids -> ids.remove(film.getId()) }
        deleteFile(getDataFileName(film))
        deleteFile(getPosterFileName(film))
        deleteFile(getExtraDataFileName(film))
    }
    fun loadFilm(id: Int): Film?{
        val data: List<String> = loadFile(getDataFileName(id))
        if (data.isEmpty()) return null
        val rawExtraData: List<String> = loadFile(getExtraDataFileName(id))
        val extraData: JsonObject? =
            if (rawExtraData.isEmpty()) null
            else JsonParser.parseString(rawExtraData[0]).asJsonObject
        return Film(JsonParser.parseString(data[0]).asJsonObject, extraData)
    }

}









