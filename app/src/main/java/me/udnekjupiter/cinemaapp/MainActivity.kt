package me.udnekjupiter.cinemaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.udnekjupiter.cinemaapp.data.FileManager
import me.udnekjupiter.cinemaapp.data.Film
import me.udnekjupiter.cinemaapp.data.KinopoiskApi
import me.udnekjupiter.cinemaapp.film.FilmCard
import me.udnekjupiter.cinemaapp.ui.theme.CinemaAppTheme

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var instance: MainActivity
        val fileManager: FileManager
            get() = FileManager(instance)
        val api: KinopoiskApi
            get() = KinopoiskApi(instance)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this

        val films = ArrayList<Film>()

        enableEdgeToEdge()
        api.getTop100 { apiFilms ->
            films.addAll(apiFilms!!)
            fileManager.unpinFilm(films[0])
            setContent {
                CinemaAppTheme {
                    Column (modifier = Modifier.padding(top = 18.dp).verticalScroll(rememberScrollState(0))){
                        for (i in 0 until films.size){
                            FilmCard(films[i])
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}



//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun DynamicButtonPreview(){
//    DynamicButton()
//}


//@Composable
//fun DynamicButton(modifier: Modifier = Modifier){
//    val count = remember{ mutableStateOf(0) }
//
//    Text(
//        text = "Clicks: ${count.value}",
//        modifier = modifier
//            .padding(20.dp)
//            .clip(RoundedCornerShape(15.dp))
//            .shadow(
//                elevation = 1.dp,
//                shape = RoundedCornerShape(15.dp)
//            )
//            .background(color = Color(0xffd0d0d0))
//            .clickable(
//                onClick = { count.value += 1 }
//            )
//            .padding(15.dp)
//            .size(count.value.dp)
//    )
//}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CinemaAppTheme {
//        Greeting("Android")
//    }
//}