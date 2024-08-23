package com.example.androidsample.ui.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidsample.SampleApplication
import com.example.androidsample.data.model.WiseSaying
import com.example.androidsample.ui.component.WiseSayingData
import com.example.androidsample.ui.component.WiseSayingDataItem
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.viewmodel.TodoViewModel
import com.example.androidsample.ui.viewmodel.WiseSayingViewModel
import java.util.Calendar

//val contentsList = listOf(
//    WiseSayingData("하루에 3시간을 걸으면 7년 후에 지구를 한바퀴 돌 수 있다.", "사무엘존슨",  isFavorite = false, isFavoriteAddDate = Calendar.getInstance().timeInMillis, wiseSayingDataThemes = "default"),
//    WiseSayingData("When you believe in a thing, believe in it all the way, implicitly and unquestionable.", "Walt Disney",  isFavorite = true, isFavoriteAddDate = Calendar.getInstance().timeInMillis, wiseSayingDataThemes = "default"),
//    WiseSayingData("Never say goodbye because goodbye means going away and going away means forgetting", "Peter Pan",  isFavorite = false, isFavoriteAddDate = Calendar.getInstance().timeInMillis, wiseSayingDataThemes = "default"),
//    WiseSayingData("Some people are worth melting for.", "Frozen(Olaf)",  isFavorite = true, isFavoriteAddDate = Calendar.getInstance().timeInMillis, wiseSayingDataThemes = "default"),
//    WiseSayingData("좋은 성과를 얻으려면 한 걸음 한 걸음이 힘차고 충실하지 않으면 안 된다, ", "단테",  isFavorite = false, isFavoriteAddDate = Calendar.getInstance().timeInMillis, wiseSayingDataThemes = "default"),
//    WiseSayingData("1퍼센트의 가능성, 그것이 나의 길이다.", "나폴레옹",  isFavorite = true, isFavoriteAddDate = Calendar.getInstance().timeInMillis, wiseSayingDataThemes = "default"),
//    WiseSayingData("고통이 남기고 간 뒤를 보라! 고난이 지나면 반드시 기쁨이 스며든다. ", "괴테",  isFavorite = false, isFavoriteAddDate = Calendar.getInstance().timeInMillis, wiseSayingDataThemes = "default"),
//)
@Composable
fun HomeScreen(navController: NavController, viewModel: TodoViewModel = hiltViewModel(),
               wiseSayingViewModel: WiseSayingViewModel = hiltViewModel()) {

    val context = SampleApplication.appContext
    val wiseSayings by wiseSayingViewModel.wiseSayings

    AndroidSampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Log.d("WiseSaying", " WiseSaying home screen 2 " + wiseSayings.size)
            if (wiseSayings.isNotEmpty()) {
                SwipeableCardView(wiseSayings, wiseSayingViewModel)
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "로딩 중입니다...")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableCardView(items: List<WiseSaying>, wiseSayingViewModel: WiseSayingViewModel) {
    val pagerState = rememberPagerState(pageCount = { items.size } )
    var cardItems by remember { mutableStateOf(items) }

        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) {page ->
            val item = cardItems[page]
            val isFlipped = false

            val rotationYs by animateFloatAsState(
                targetValue = if (isFlipped) 180f else 0f,
                animationSpec = tween(durationMillis = 600), label = ""
            )

            val frontVisibility = if (rotationYs <= 90f) 1f else 0f
            val backVisibility = if (rotationYs > 90f) 1f else 0f

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    cardItems = cardItems.mapIndexed { index, cardItem ->
                                        if (index == page) {
                                            cardItem
//                                            cardItem.copy(isFlipped)
                                        } else {
                                            cardItem
                                        }
                                    }
                                }
                            )
                        }
                        .graphicsLayer {
                            rotationY = rotationYs
                            cameraDistance = 12f * density
                        }
                ) {
                    if (frontVisibility == 1f) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                IconButton(onClick = {
                                    cardItems = cardItems.mapIndexed { index, cardItem ->
                                        if (index == page) {
                                            val updateItem = cardItem.copy(isFavorite = (if (cardItem.isFavorite == 1) 0 else 1))
                                            wiseSayingViewModel.updateIsFavorite(updateItem.uid)
                                            updateItem

                                        } else {
                                            cardItem
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "Favorite",
                                        tint = if (item.isFavorite == 1) Color.Red else Color.Gray,
                                    )
                                }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    item.contents?.let {
                                        Text(text = it,
                                            overflow = TextOverflow.Ellipsis,
                                            fontSize = 30.sp,
                                            textAlign = TextAlign.Center,
                                            fontStyle = FontStyle.Normal,
                                            lineHeight = 30.sp,
                                            color = Color.Black)
                                    }
                                    item.author?.let {
                                        Text(text = it,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Center,
                                            fontStyle = FontStyle.Italic,
                                            modifier = Modifier.padding(10.dp))
                                    }
                                }
                            }
                        }
                    }

                    if (backVisibility == 1f) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    item.author?.let {
                                        Text(text = it,
                                            modifier = Modifier.graphicsLayer {
                                                alpha = backVisibility
                                                rotationY = rotationYs
                                            },
                                            overflow = TextOverflow.Ellipsis,
                                            fontSize = 30.sp,
                                            textAlign = TextAlign.Center,
                                            fontStyle = FontStyle.Normal,
                                            lineHeight = 30.sp,
                                            color = Color.Black)
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
}

@Preview
@Composable
fun TestHomeScreen() {
//   SwipeableCardView(contentsList)
}