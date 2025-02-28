package me.udnekjupiter.cinemaapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import me.udnekjupiter.cinemaapp.data.SerializableFilm

class FilmActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_film)

        val film = intent.getSerializableExtra("film", SerializableFilm::class.java) as SerializableFilm

        //val film: SerializableFilm? = arguments?.getSerializable("Film", SerializableFilm::class.java)

        setContent {
        }
    }
}