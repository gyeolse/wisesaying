package com.example.androidsample.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidsample.data.model.WiseSaying
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.viewmodel.WiseSayingViewModel
import java.time.LocalDate

@Composable
fun FavoriteScreen(navController: NavController,
                   wiseSayingViewModel: WiseSayingViewModel = hiltViewModel()) {
//    val favoriteWiseSayings by wiseSayingViewModel.favoriteWiseSayings.collectAsState(emptyList())
    val favoriteWiseSayings by wiseSayingViewModel.favoriteWiseSayings.observeAsState(emptyList())

    // 로그를 통해 상태 변화 감지
    LaunchedEffect(favoriteWiseSayings) {
        wiseSayingViewModel.fetchFavoriteWiseSayings()
        Log.d("FavoriteScreen", "State changed - favoriteWiseSayings.size=${favoriteWiseSayings.size}")
    }

    AndroidSampleTheme {
        val scrollState = rememberScrollState()
        Log.d("FavoriteScreen", "CALLED=" + favoriteWiseSayings.size.toString() )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(favoriteWiseSayings) { wiseSaying ->
                QuoteItem(
                    wiseSaying = wiseSaying,
                )
            }
//        item {
//            Spacer(modifier = Modifier.height(16.dp))
//
////            Button(
////                onClick = {
////                    navController.navigate("home_screen")
////                },
////                modifier = Modifier.align(Alignment.CenterHorizontally)
////            ) {
////                Text("즐겨찾기 추가하기")
////            }
//        }
        }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(scrollState)
//            .padding(16.dp)
//    ) {
//        favoriteWiseSayings.forEach { wiseSaying ->
//            QuoteItem(wiseSaying)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // "즐겨찾기 추가하기" 버튼
//        Button(
//            onClick = {
//                // 홈 화면으로 이동 (HomeScreen.kt으로)
//                navController.navigate("home_screen")
//            },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("즐겨찾기 추가하기")
//        }
//    }
    }
}
@Composable
fun FavoriteQuotesScreen(navController: NavController, wiseSayingViewModel: WiseSayingViewModel) {
}

@Composable
fun QuoteItem(wiseSaying: WiseSaying) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = wiseSaying.contents,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "- ${wiseSaying.author}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "추가된 날짜: ${wiseSaying.isFavoriteAddDate}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

