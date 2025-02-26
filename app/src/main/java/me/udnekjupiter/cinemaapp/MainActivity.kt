package me.udnekjupiter.cinemaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.udnekjupiter.cinemaapp.film.FilmCard
import me.udnekjupiter.cinemaapp.ui.theme.CinemaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinemaAppTheme {
                val imageURL = "https://i.pinimg.com/736x/e4/b8/6d/e4b86d0875995ec448dc7ead95f.jpg"
                Column (modifier = Modifier.padding(top = 18.dp).verticalScroll(rememberScrollState(0))){
                    for (i in 1..20){
                        FilmCard(imageURL)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}



//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun DynamicButtonPreview(){
//    DynamicButton()
//}


@Composable
fun DynamicButton(modifier: Modifier = Modifier){
    val count = remember{ mutableStateOf(0) }

    Text(
        text = "Clicks: ${count.value}",
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(15.dp))
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(color = Color(0xffd0d0d0))
            .clickable(
                onClick = { count.value += 1 }
            )
            .padding(15.dp)
            .size(count.value.dp)
    )
}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CinemaAppTheme {
//        Greeting("Android")
//    }
//}