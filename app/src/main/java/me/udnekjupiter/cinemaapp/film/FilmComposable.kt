package me.udnekjupiter.cinemaapp.film

import android.content.Context
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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import me.udnekjupiter.cinemaapp.R
import me.udnekjupiter.cinemaapp.data.Film

@Composable
fun FilmCard(film: Film, context: Context, modifier: Modifier = Modifier){
    Log.d("FilmCard", "${film.getPosterUrl()}")

    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(125.dp)
            .fillMaxWidth()
            .background(
                color = Color(0xFFE8E8E8),
                shape = RoundedCornerShape(8.dp)
            )
            .then(modifier)
    ){
        Row {
            FilmPoster(
                film.getPosterUrl().toString(),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = film.getRuName(),
                    modifier = Modifier
                        .padding(start = 15.dp, end = 40.dp)
                        .height(30.dp)
                        .fillMaxWidth(),
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
fun FavoriteFilmButton(filmToPin: Film){
    val isPinned = remember { mutableStateOf(filmToPin.isPinned()) }

    val imageSource = when (isPinned.value) {
        true -> R.drawable.black_star
        false -> R.drawable.hollow_black_star }

    Box(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ){
        Image(
            painter = painterResource(id = imageSource),
            contentDescription = "FavIcon",
            modifier = Modifier
                .clickable(
                    onClick = {
                        when (isPinned.value){
                            true -> filmToPin.unpin()
                            false -> filmToPin.pin()
                        }
                        isPinned.value = isPinned.value.not()
                        Log.d("PinButton", "Film (${filmToPin.getRuName()}) pinned: ${filmToPin.isPinned()}")
                    }
                )
                .padding(5.dp)
                .size(30.dp, 30.dp)
        )
    }
}

@Composable
fun FilmPoster(posterURL: String, modifier: Modifier = Modifier, contentScale: ContentScale = ContentScale.FillWidth){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterURL)
            .crossfade(true)
            .build(),
        contentDescription = "Film Poster",
        contentScale = contentScale,
        modifier = modifier

    )
}