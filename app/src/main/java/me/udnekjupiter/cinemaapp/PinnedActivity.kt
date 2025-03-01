package me.udnekjupiter.cinemaapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import me.udnekjupiter.cinemaapp.MainActivity.Companion.instance
import me.udnekjupiter.cinemaapp.data.Film
import me.udnekjupiter.cinemaapp.film.FilmCard
import me.udnekjupiter.cinemaapp.ui.theme.CinemaAppTheme

class PinnedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val films = Film.loadAllPinned()
        enableEdgeToEdge()
        setContent {
            CinemaAppTheme {
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .verticalScroll(rememberScrollState(0))
                ) {
                    Log.d("MainActivity", "Films parsed: ${films.size}")
                    for (i in 0 until films.size) {
                        FilmCard(
                            films[i],
                            instance,
                            modifier = Modifier
                                .clickable(onClick = {
                                    val intent = Intent(instance, FilmActivity::class.java)
                                    intent.putExtra("film", films[i].serialize())
                                    startActivity(intent)
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}