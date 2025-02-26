package me.udnekjupiter.cinemaapp.test

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.udnekjupiter.cinemaapp.data.KinopoiskApi
import me.udnekjupiter.cinemaapp.film.FilmCard
import me.udnekjupiter.cinemaapp.ui.theme.CinemaAppTheme
import java.io.File
import kotlin.reflect.KSuspendFunction1

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinemaAppTheme {
                Test(this)
            }
        }
    }
}

@Composable
fun Test(context: Context, modifier: Modifier = Modifier){
    val coroutineScope = rememberCoroutineScope()
    val counters = List(25) { remember { mutableStateOf(0) } }
    var counter2 = remember { mutableStateOf(250_000) }
    val api = KinopoiskApi(context)

    Column(modifier
        .fillMaxWidth()
        .background(
            shape = RectangleShape,
            color = Color(0xFFB6B6B6)
        )
        .verticalScroll(
            state = rememberScrollState()
        )
    ){
        for (i in 0..24){
            TrackerLine(counters[i], ::asyncTask1, coroutineScope)
        }
    }
}

suspend fun asyncTask1(input: MutableState<Int>){
    for (n in 0..250_000){
        input.value += 1
        delay(1000)
    }
}
suspend fun asyncTask2(input: MutableState<Int>){
    for (n in 0..250_000){
        input.value -= 1
        delay(1000)
    }
}

@Composable
fun TrackerLine(
    trackingValue: MutableState<Int>,
    task: KSuspendFunction1<MutableState<Int>, Unit>,
    coroutineScope: CoroutineScope){
    val buttonEnabled = remember { mutableStateOf(true) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable(
            enabled = buttonEnabled.value,
            onClick = { coroutineScope.launch { task(trackingValue) } }
        )
        .border(
            width = 2.dp,
            shape = RectangleShape,
            color = Color.Black
        )
    ){
        Text(
            text = trackingValue.value.toString(),
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(5.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TrackerLinePreview(){
    val coroutineScope = rememberCoroutineScope()

    TrackerLine(remember { mutableStateOf(42) }, ::asyncTask1, coroutineScope)
}