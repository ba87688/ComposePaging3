package com.example.splashapicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.splashapicompose.navigation.SetupNavGraph
import com.example.splashapicompose.ui.theme.SplashAPIComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashAPIComposeTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}