package me.udnekjupiter.cinemaapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.udnekjupiter.cinemaapp.MainActivity.Companion.instance
import me.udnekjupiter.cinemaapp.data.Film
import me.udnekjupiter.cinemaapp.data.SerializableFilm

class FilmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_film)

        val film = intent.getSerializableExtra("Film") as SerializableFilm

        //val film: SerializableFilm? = arguments?.getSerializable("Film", SerializableFilm::class.java)

        setContent {
        }
    }
}