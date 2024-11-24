package com.example.androidsample.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidsample.SampleApplication
import com.example.androidsample.data.model.WiseSaying
import com.example.androidsample.ui.component.WiseSayingData
import com.example.androidsample.ui.component.WiseSayingDataItem
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.viewmodel.TodoViewModel
import com.example.androidsample.ui.viewmodel.WiseSayingViewModel
import kotlinx.coroutines.launch
import android.net.Uri
import android.os.Environment
import android.os.Environment.*
import android.view.ViewGroup
import androidx.compose.foundation.text.BasicText
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import android.graphics.Canvas
import androidx.compose.ui.text.font.FontFamily
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: TodoViewModel = hiltViewModel(),
               wiseSayingViewModel: WiseSayingViewModel = hiltViewModel(), selectedUid: Int? = null) {

    val context = SampleApplication.appContext
    val wiseSayings by wiseSayingViewModel.wiseSayings

    AndroidSampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (wiseSayings.isNotEmpty()) {
                SwipeableCardView(wiseSayings, wiseSayingViewModel, selectedUid, context)
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "로딩 중입니다...", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableCardView(items: List<WiseSaying>, wiseSayingViewModel: WiseSayingViewModel, selectedUid: Int?, context: Context) {
    val pagerState = rememberPagerState(pageCount = { items.size } )
    var cardItems by remember { mutableStateOf(items) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var hasRandomPageBeenSet by remember { mutableStateOf(false) }

    // 선택된 uid에 따라 해당 페이지로 스크롤
    LaunchedEffect(items, selectedUid) {
        if (selectedUid != null) {
            val selectedIndex = items.indexOfFirst { it.uid == selectedUid }
            if (selectedIndex >= 0) {
                pagerState.scrollToPage(selectedIndex)
            }
        } else {
            try {
                if (!hasRandomPageBeenSet) {
                    val randomIndex = (items.indices).random()
                    pagerState.scrollToPage(randomIndex)
                    hasRandomPageBeenSet = true
                }
            } catch (e: Exception) {
                Log.d("HomeScreen", "Exceptions")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) {
        innerPadding ->
        VerticalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = pagerState
        ) {page ->
            val item = cardItems[page]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.White),
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
                                        } else {
                                            cardItem
                                        }
                                    }
                                }
                            )
                        }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .background(Color.White), // 카드 배경을 흰색으로 설정,
                        colors = androidx.compose.material3.CardDefaults.cardColors(
                        containerColor = Color.White // 카드 배경을 흰색으로 명시적으로 설정
                        )
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
                                            scope.launch {
                                                snackbarHostState.currentSnackbarData?.dismiss()

                                                if (cardItem.isFavorite == 1) {
                                                    snackbarHostState.showSnackbar(message = "즐겨찾기에 삭제되었습니다.", duration = SnackbarDuration.Short)
                                                } else {
                                                    snackbarHostState.showSnackbar(message = "즐겨찾기에 추가되었습니다.", duration = SnackbarDuration.Short)
                                                }
                                            }
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

                                ShareButton(shareText = "${item.contents} - ${item.author ?: "Unknown"}")
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
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.SansSerif,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 30.sp,
                                        textAlign = TextAlign.Center,
                                        fontStyle = FontStyle.Normal,
                                        lineHeight = 40.sp,
                                        letterSpacing = 2.sp,
                                        color = Color.Black)
                                }
                                item.author?.let {
                                    Text(text = it,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        fontStyle = FontStyle.Italic,
                                        modifier = Modifier.padding(10.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShareButton(shareText: String) {
    val context = LocalContext.current

    IconButton(onClick = {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)  // 플래그 추가
        }

        val chooser = Intent.createChooser(shareIntent, "명언 공유하기")
        context.startActivity(chooser)
    }) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = "Share",
            tint = Color.Blue
        )
    }
}