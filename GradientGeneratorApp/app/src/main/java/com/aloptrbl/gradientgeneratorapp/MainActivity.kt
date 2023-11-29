package com.aloptrbl.gradientgeneratorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aloptrbl.gradientgeneratorapp.ui.theme.GradientGeneratorAppTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GradientGeneratorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val items = listOf(
        Screen.Home,
        Screen.Explore,
        Screen.Save
    )
    var selectedItem by remember { mutableStateOf(Screen.Home) }
    Scaffold(
        bottomBar = {
            BottomTabBar(navController, items)
        },
        topBar = {
            TopAppBar(
                title = { Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "LUMI")
                } },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { coroutineScope.launch { drawerState.open() } }, enabled = false,
                        modifier = Modifier.alpha(0f)
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            )
        },
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Explore.route) { ExploreScreen() }
            composable(Screen.Save.route) { SaveScreen() }
        }
    }

}

@Composable
fun BottomTabBar(navController: NavHostController, items: List<Screen>, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var selectedScreen by remember { mutableStateOf(0) }
    var bottomWidth by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.9f))
            .fillMaxWidth()
            .onGloballyPositioned {
                bottomWidth = it.size.width
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (item in items) {
            val isSelected = item == items[selectedScreen]
            Box(
                modifier = Modifier
                .height(50.dp)
                    .width(120.dp)
                .background(Color.Unspecified)
                .clickable(onClick = {
                    selectedScreen = items.indexOf(item)
                    navController.navigate(item.route)
                }
                ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    verticalArrangement =  Arrangement.Center,
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = item.icon),
                        contentDescription = "icon",
                        tint = if(isSelected) Color.Blue else Color.Black,
                        modifier = Modifier.size(45.dp)
                    )
                }
            }
        }
    }
}

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Home : Screen("home", Icons.Default.Home, "Home")
    object Explore : Screen("explore", Icons.Default.Search, "Explore")
    object Save : Screen("save", Icons.Default.FavoriteBorder, "Save")
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GradientGeneratorAppTheme {
        App()
    }
}