package com.aloptrbl.agslshaderexperimentapp.ui.components

import android.content.Context
import android.graphics.RuntimeShader
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aloptrbl.agslshaderexperimentapp.ui.screens.ScreenFive
import com.aloptrbl.agslshaderexperimentapp.ui.screens.ScreenFour
import com.aloptrbl.agslshaderexperimentapp.ui.screens.ScreenOne
import com.aloptrbl.agslshaderexperimentapp.ui.screens.ScreenSix
import com.aloptrbl.agslshaderexperimentapp.ui.screens.ScreenThree
import com.aloptrbl.agslshaderexperimentapp.ui.screens.ScreenTwo
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalTabView(
    shader: RuntimeShader,
    shader2: RuntimeShader,
    shader3: RuntimeShader,
    gradientShader: RuntimeShader,
    context: Context
) {
    // Remember the pager state
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabTitles = listOf("Introduction", "Basic", "Gradient", "Image Manipulation", "Recap", "Advanced")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column() {
        // TabRow with three tabs
        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                val tabWidth = tabPositions[pagerState.currentPage].width
                val indicatorWidth = 50.dp
                val offset = (tabWidth - indicatorWidth) / 3
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(5.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(color = Color.Blue)
                )
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        // Animate to the selected tab's page
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            pageCount = tabTitles.size,
            state = pagerState,
            // Add padding to the pager
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { page ->
            // Display content based on the current page
            when (page) {
                0 -> {ScreenOne()}
                1 -> ScreenTwo(shader, shader2, shader3)
                2 -> ScreenThree(gradientShader)
                3 -> ScreenFour(context.resources)
                4 -> ScreenFive()
                5 -> ScreenSix(context.resources)
            }
        }
    }
}

