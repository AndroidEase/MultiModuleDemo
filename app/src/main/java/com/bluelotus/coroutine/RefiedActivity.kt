package com.bluelotus.coroutine

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bluelotus.coroutine.ui.theme.CoroutineTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class RefiedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Refied(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

sealed class Video() {
    data class Programming(val title : String, val duration : String) : Video()
    data class Cooking(val title : String, val duration : String) : Video()
    data class Travel(val title : String, val duration : String) : Video()
}

inline fun<reified T> filter(videos : List<Video>) : List<T> {
    return videos.filterIsInstance<T>()
}


inline fun applyTransformation(
    videos : List<Video>,
    noinline transformation : (Video) -> Video,
    crossinline onComplete : (List<Video>) -> Unit
) {
   val v = videos.map(transformation)
    onComplete.invoke(v)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Refied(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

    val videos = listOf(
        Video.Programming("Kotlin Basics", "10:30"),
        Video.Cooking("Rice", "10:30"),
        Video.Programming("Kotlin For Begginners", "20:12"),
        Video.Travel("Goa", "10:30")
    )

    val filter = filter<Video.Programming>(videos)
    println("ABC Filter List" + filter.toString())

    applyTransformation(videos,
        transformation = {
            when(it) {
                is Video.Programming -> {
                   it.copy(title = it.title+"transformed")
                }
                is Video.Cooking -> {
                    it.copy(title = it.title+"transformed")
                }
                is Video.Travel -> {
                    it.copy(title = it.title+"transformed")
                }
            }
        },
        onComplete = {

        })

//    runBlocking {
//        launch {
//            println("ABC world")
//        }
//        println("ABC hello")
//    }

//    GlobalScope.launch {
//        executeTask()
//    }

}
