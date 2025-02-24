package me.udnekjupiter.cinemaapp.film

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilmListTest(filmList: List<String> ,modifier: Modifier = Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(
                state = ScrollState(0)
            )
            .fillMaxWidth()
    ){
        for (film in filmList) {
            Box(
                modifier = modifier
                    .padding(2.dp)
                    .background(
                        shape = RectangleShape,
                        color = Color(0xffe8e8e8)
                    )
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        shape = RectangleShape,
                        color = Color.Black
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = film,
                    modifier = modifier
                        .padding(6.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun FilmListTestPreview(){
    val testFilms = listOf(
        "MegaSigma",
        "Flying Fly",
        "Crazy Raccoon",
        "My name is John Daker",
        "Uranium Shoes",
        "Bnuuy",
        "Серебряный слон",
        "Ожидайте неожиданностей",
        "Top 15 Churchill quotes",
        "What have you done?",
        "What have you done?",
        "What have you done?",
        "What have you done?",
        "What have you done?",
        "What have you done?",
        "What have you done?"
    )

    FilmListTest(testFilms)
}