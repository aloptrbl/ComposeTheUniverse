package com.aloptrbl.infinitescrollhorizontalpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloptrbl.infinitescrollhorizontalpager.model.Item
import com.aloptrbl.infinitescrollhorizontalpager.ui.theme.InfiniteScrollHorizontalPagerTheme

class MainActivity : ComponentActivity() {

    val items: Array<Item> = arrayOf(
        Item(Color.Blue),
        Item(Color.Red),
        Item(Color.Yellow),
        Item(Color.Green)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfiniteScrollHorizontalPagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Infinite Scroll Horizontal Pager",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                        CarouselView(items)
                        Spacer(Modifier.padding(20.dp))
                        HorizontalPager(items)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselView(items: Array<Item>?) {
    val pageCount = Int.MAX_VALUE // Used for infinity scroll
    var pagerState: PagerState = rememberPagerState()
    items?.size?.let {
        HorizontalPager(pageCount = pageCount, Modifier.fillMaxWidth(), state = pagerState, contentPadding = PaddingValues(horizontal = 20.dp), pageSpacing = 10.dp) { page ->
            val realPage = page % items.size  // Loop over your static array
            Row(
                Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(items[realPage].color), horizontalArrangement = Arrangement.SpaceBetween) {
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPager(items: Array<Item>?) {
    val pageCount = Int.MAX_VALUE // Used for infinity scroll
    var pagerState: PagerState = rememberPagerState()
    items?.size?.let {
        HorizontalPager(pageCount = pageCount, state = pagerState, contentPadding = PaddingValues(horizontal = 20.dp), pageSpacing = 10.dp) { page ->
            val realPage = page % items.size  // Loop over your static array
            Row(
                Modifier
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .width(300.dp)
                    .height(300.dp)
                    .background(items[realPage].color)
                  ) {
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InfiniteScrollHorizontalPagerTheme {

    }
}