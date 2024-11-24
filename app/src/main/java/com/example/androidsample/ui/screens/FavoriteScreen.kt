package com.example.androidsample.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import com.example.androidsample.ui.navigation.ScreenInfo
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.viewmodel.WiseSayingViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController,
                   wiseSayingViewModel: WiseSayingViewModel = hiltViewModel()) {
    val favoriteWiseSayings by wiseSayingViewModel.favoriteWiseSayings.observeAsState(emptyList())

    // 로그를 통해 상태 변화 감지
    LaunchedEffect(favoriteWiseSayings) {
        wiseSayingViewModel.fetchFavoriteWiseSayings()
        Log.d("FavoriteScreen", "State changed - favoriteWiseSayings.size=${favoriteWiseSayings.size}")
    }
    AndroidSampleTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("즐겨찾기", fontSize = 18.sp, fontWeight = FontWeight.Bold,) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "뒤로 가기"
                            )
                        }
                    },
                    // [TODO] Need to add Search, Sort functions
//                    actions = {
//                        IconButton(onClick = { /* 검색 기능 추가 */ }) {
//                            Icon(
//                                imageVector = Icons.Default.Search,
//                                contentDescription = "검색"
//                            )
//                        }
//                        IconButton(onClick = { /* 정렬 기능 추가 */ }) {
//                            Icon(
//                                imageVector = Icons.Default.List,
//                                contentDescription = "정렬"
//                            )
//                        }
//                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = "즐겨찾은 문구들 📚",
                        fontSize = 18.sp, fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                if (favoriteWiseSayings.isEmpty()) {
                    item {
                        Text(
                            text = "아직 즐겨찾기에 추가된 문구가 없습니다.",
                            fontSize = 18.sp, fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
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
                                        inclusive = true // HomeScreen 중복 방지
                                        saveState = false // 상태 저장 필요 없음
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
    }
//    AndroidSampleTheme {
//        val scrollState = rememberScrollState()
//        Log.d("FavoriteScreen", "CALLED=" + favoriteWiseSayings.size.toString() )
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            items(favoriteWiseSayings) { wiseSaying ->
//                QuoteItem(
//                    wiseSaying = wiseSaying,
//                    onClick = {
//                        navController.navigate("${ScreenInfo.Home.route}/${wiseSaying.uid}") {
//                            Log.d("Navigation", "Navigating to: ${ScreenInfo.Home.route}/${wiseSaying.uid}")
//
//                            popUpTo(ScreenInfo.Home.route) {
//                                inclusive = true // HomeScreen이 이미 있을 경우 중복 방지
//                                saveState = false // 새로운 화면으로 이동 시 상태 저장 불필요
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                )
//            }
//        }
//    }


}

//@Composable
//fun QuoteItem(
//    wiseSaying: WiseSaying,
//    onClick: () -> Unit ) {
//    Column(
//        modifier = Modifier
//            .padding(8.dp)
//            .clickable { onClick() }
//    ) {
//        Text(
//            text = wiseSaying.contents,
//            style = MaterialTheme.typography.bodyLarge,
//            color = Color.Black
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = "- ${wiseSaying.author}",
//            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Gray
//        )
//        Spacer(modifier = Modifier.height(2.dp))
//        Text(
//            text = "추가된 날짜: ${wiseSaying.isFavoriteAddDate}",
//            style = MaterialTheme.typography.bodySmall,
//            color = Color.Gray
//        )
//        Spacer(modifier = Modifier.height(12.dp))
//    }
//}

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
            fontSize = 18.sp, fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${wiseSaying.isFavoriteAddDate} - ${wiseSaying.author}",
            fontSize = 18.sp, fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
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
