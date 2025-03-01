package me.udnekjupiter.cinemaapp

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import me.udnekjupiter.cinemaapp.data.SerializedFilm
import me.udnekjupiter.cinemaapp.film.FilmPoster
import me.udnekjupiter.cinemaapp.ui.theme.CinemaAppTheme

class FilmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serializedFilm = intent.getSerializableExtra("film") as SerializedFilm
        val film = serializedFilm.deserialize()

        //val film: SerializableFilm? = arguments?.getSerializable("Film", SerializableFilm::class.java)

        setContent {
            CinemaAppTheme {
                val coroutineScope = rememberCoroutineScope()
                val filmDescription = remember { mutableStateOf("") }

                LaunchedEffect(key1 = Unit) {
                    coroutineScope.launch {
                        film.loadExtraData {
                            film ->
                            filmDescription.value = film.getLoadedDescription().toString()
                        }
                    }
                }


                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .verticalScroll(rememberScrollState(0))
                ){
                    FilmPoster(
                        posterURL = film.getPosterUrl().toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ){
                        Text(
                            text = film.getRuName(),
                            fontSize = 40.sp,
                            fontWeight = FontWeight(750),
                            textAlign = TextAlign.Center,
                            lineHeight = 37.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Text(
                            text = filmDescription.value,
                            fontWeight = FontWeight(400),
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )
                        Text(
                            text = "Жанры: ${TextUtils.join(", ", film.getGenres())}",
                            modifier = Modifier
                                .padding(top = 10.dp)
                        )
                        Text(
                            text = "Год выхода: ${film.getYear()}"
                        )
                    }
                }
            }
        }
    }
}