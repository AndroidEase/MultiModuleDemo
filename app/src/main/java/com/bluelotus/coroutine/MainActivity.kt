package com.bluelotus.coroutine

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bluelotus.coroutine.navigation.NavigationSubGraph
import com.bluelotus.coroutine.navigation.RecipeNavigation
import com.bluelotus.coroutine.ui.theme.CoroutineTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationSubGraph: NavigationSubGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineTheme {
               Surface(modifier = Modifier.safeContentPadding()) {
                   RecipeNavigation(navigationSubGraph = navigationSubGraph)
               }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

//    runBlocking {
//        launch {
//            println("ABC world")
//        }
//        println("ABC hello")
//    }

//    GlobalScope.launch {
//        executeTask()
//    }

    printFollowersSequentially()
}

 fun printFollowersParallely() {

     GlobalScope.launch {

         var fbFollower = GlobalScope.async {
             val fbFollower = getFbFollower()
             fbFollower
         }

         var instaFollower = GlobalScope.async {
             val instaFollower = getInstaFollower()
             instaFollower
         }

         println("Follower : FB" + fbFollower.await())
         println("Follower : FB" + instaFollower.await())
     }
}

fun printFollowersSequentially() {

    GlobalScope.launch {

        val fbFollower = getFbFollower()
        val instaFollower = getInstaFollower()

        println("Follower : FB" + fbFollower)
        println("Follower : FB" + instaFollower)
    }
}

suspend fun testParentChildJob() {
    val parentJob = GlobalScope.launch(Dispatchers.Main) {
        Log.d("ABC", "Parent Job Started")

        val childJob = launch(Dispatchers.IO) {
            Log.d("ABC", "Child Job Started")
            delay(5000)
            Log.d("ABC", "Child Job Ended")
        }

        delay(3000)
        Log.d("ABC", "Parent Job Ended")
    }
    parentJob.join()
    Log.d("ABC", "Parent Job Completed")
}

suspend fun getFbFollower()  : Int {
    delay(1000)
    return 10
}

suspend fun getInstaFollower()  : Int {
    delay(1000)
    return 10
}

suspend fun executeTask() {
    println("ABC Before")
    withContext(Dispatchers.IO) {
        delay(1000)
        println("ABC Inside")
    }
    println("ABC After")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutineTheme {
        Greeting("Android")
    }
}