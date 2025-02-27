package me.udnekjupiter.cinemaapp.film

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.*
import me.udnekjupiter.cinemaapp.R
import me.udnekjupiter.cinemaapp.data.Film

@Composable
fun FilmCard(film: Film){

    val painter = rememberAsyncImagePainter(
        model = film.getPosterUrl(),
        placeholder = BitmapPainter(ImageBitmap.imageResource(R.drawable.image_placeholder))
    )

    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(125.dp)
            .fillMaxWidth()
            .background(
                color = Color(0xFFE8E8E8),
                shape = RoundedCornerShape(8.dp)
            )
    ){
        Row {
            Image(
                painter = painter,
                contentDescription = "Film Poster",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight()
            )
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .border(2.dp, color = Color.Black)) {
                Text(
                    text = film.getRuName(),
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(width = 280.dp, height = 20.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${film.getGenres()[0]} (${film.getYear()})",
                    modifier = Modifier
                        .padding(start = 15.dp),
                    color = Color.Gray
                )
            }
        }
        FavoriteFilmButton(film)
    }
}

@Composable
fun FavoriteFilmButton(filmToFavorite: Film){
    val filmFavorited = remember { mutableStateOf( false ) }

    val imageSource = when (filmFavorited.value) {
        true -> R.drawable.black_star
        false -> R.drawable.hollow_black_star }

    Box(

    ){
        Image(
            painter = painterResource(id = imageSource),
            contentDescription = "FavIcon",
            modifier = Modifier
                .clickable(
                    onClick = {
                        filmFavorited.value = filmFavorited.value.not()
                        Log.d("FavButton", "filmFavorited: ${filmFavorited.value}")
                    }
                )
        )
    }
}


@Composable
fun FilmListTest(filmList: List<String>, modifier: Modifier = Modifier){
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