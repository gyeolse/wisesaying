package com.example.androidsample.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidsample.data.model.WiseSaying
import com.example.androidsample.ui.component.CustomTopAppBar
import com.example.androidsample.ui.navigation.ScreenInfo
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.viewmodel.WiseSayingViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController,
                   wiseSayingViewModel: WiseSayingViewModel = hiltViewModel()) {
    val favoriteWiseSayings by wiseSayingViewModel.favoriteWiseSayings.observeAsState(emptyList())

    LaunchedEffect(favoriteWiseSayings) {
        wiseSayingViewModel.fetchFavoriteWiseSayings()
        Log.d("FavoriteScreen", "State changed - favoriteWiseSayings.size=${favoriteWiseSayings.size}")
    }
    AndroidSampleTheme {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = "즐겨찾기",
                    onBackClick = { navController.popBackStack() }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                if (favoriteWiseSayings.isEmpty()) {
                    // 중앙에 정렬된 텍스트
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "아직 즐겨찾기에 추가된 문구가 없습니다. 🔥",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(favoriteWiseSayings) { wiseSaying ->
                            QuoteItem(
                                wiseSaying = wiseSaying,
                                onClick = {
                                    navController.navigate("${ScreenInfo.Home.route}/${wiseSaying.uid}") {
                                        Log.d(
                                            "Navigation",
                                            "Navigating to: ${ScreenInfo.Home.route}/${wiseSaying.uid}"
                                        )

                                        popUpTo(ScreenInfo.Home.route) {
                                            inclusive = true
                                            saveState = false
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues) // TopAppBar 아래 공간 확보
//                    .padding(horizontal = 16.dp) // 양쪽에 여백 추가
//            ) {
//                Text(
//                    text = "즐겨찾는 문구들 ✏️",
//                    fontSize = 18.sp, fontWeight = FontWeight.Bold,
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                ) {
//                    if (favoriteWiseSayings.isEmpty()) {
//                        item {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxSize(), // 전체 화면 크기로 확장
//                                verticalArrangement = Arrangement.Center, // 수직 중앙 정렬
//                                horizontalAlignment = Alignment.CenterHorizontally // 수평 중앙 정렬
//                            ) {
//                                Text(
//                                    text = "아직 즐겨찾기에 추가된 문구가 없습니다.",
//                                    fontSize = 18.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    style = MaterialTheme.typography.bodyMedium,
//                                    color = Color.Gray,
//                                    modifier = Modifier
//                                        .padding(horizontal = 16.dp), // 가로 여백 추가
//                                    textAlign = TextAlign.Center
//                                )
//                            }
//                        }
//                    } else {
//                        items(favoriteWiseSayings) { wiseSaying ->
//                            QuoteItem(
//                                wiseSaying = wiseSaying,
//                                onClick = {
//                                    navController.navigate("${ScreenInfo.Home.route}/${wiseSaying.uid}") {
//                                        Log.d(
//                                            "Navigation",
//                                            "Navigating to: ${ScreenInfo.Home.route}/${wiseSaying.uid}"
//                                        )
//
//                                        popUpTo(ScreenInfo.Home.route) {
//                                            inclusive = true // HomeScreen 중복 방지
//                                            saveState = false // 상태 저장 필요 없음
//                                        }
//                                        launchSingleTop = true
//                                        restoreState = true
//                                    }
//                                }
//                            )
//                        }
//                    }
//                }
//            }
        }
    }
}

@Composable
fun QuoteItem(
    wiseSaying: WiseSaying,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .background(Color(0xFFF8F8F8), shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = wiseSaying.contents,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = wiseSaying.author,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            wiseSaying.isFavoriteAddDate?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun StyledText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.padding(bottom = 8.dp)
    )
}
