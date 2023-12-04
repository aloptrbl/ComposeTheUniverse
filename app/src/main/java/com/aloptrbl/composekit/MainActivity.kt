package com.aloptrbl.composekit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aloptrbl.composekit.ui.components.H1
import com.aloptrbl.composekit.ui.components.H2
import com.aloptrbl.composekit.ui.components.H3
import com.aloptrbl.composekit.ui.components.H4
import com.aloptrbl.composekit.ui.theme.ComposeKitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeKitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        H1("Hello Android")
                        H2("Hello Android")
                        H3("Hello Android")
                        H4("Hello Android")
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeKitTheme {
        H1("Android")
    }
}