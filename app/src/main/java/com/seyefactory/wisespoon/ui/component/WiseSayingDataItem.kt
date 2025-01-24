package com.seyefactory.wisespoon.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seyefactory.wisespoon.ui.viewmodel.WiseSayingViewModel

@Composable
fun WiseSayingDataItem(wiseSayingData: WiseSayingData, wiseSayingViewModel: WiseSayingViewModel = hiltViewModel()) {

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(18.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center) // 명언과 저자를 카드의 가운데에 배치
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // 가로로 가운데 정렬
            ) {
                Text(
                    text = wiseSayingData.contents, // 명언 내용
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center, // 텍스트를 가운데 정렬
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = wiseSayingData.author, // 저자 이름
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center, // 텍스트를 가운데 정렬
                )
            }

            IconButton(
                onClick = {
                    Log.d("WiseSayingDataItem", " value clicks" + wiseSayingData.uid.toString())
                    wiseSayingViewModel.updateIsFavorite(wiseSayingData.uid)
                          },
                modifier = Modifier
                    .align(Alignment.TopEnd) // 카드의 오른쪽 상단에 배치
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (wiseSayingData.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            }
        }
    }

//
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 10.dp)
//            .fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .background(Color.White)
//                .padding(18.dp)
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally, // 콘텐츠를 가로로 가운데 정렬
//            verticalArrangement = Arrangement.Center // 콘텐츠를 세로로 가운데 정렬
//        ) {
//            Text(
//                text = wiseSayingData.title, // 명언 내용
//                color = Color.Black,
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                textAlign = TextAlign.Center, // 텍스트를 가운데 정렬
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = wiseSayingData.contents, // 저자 이름
//                color = Color.Gray,
//                fontSize = 14.sp,
//                textAlign = TextAlign.Center, // 텍스트를 가운데 정렬
//            )
//        }
//    }

//    Card(modifier = Modifier
//        .padding(horizontal = 16.dp, vertical = 10.dp)
//        .fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp))  {
//
//        Column(Modifier.background(Color.White)) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(16f / 9f)
//            ) {
//                Image(painter = painterResource(id = R.drawable.picture1), contentDescription = "null",
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .aspectRatio(16f / 9f),
//                    contentScale = ContentScale.Crop)
//
//                IconButton(onClick = { /*TODO*/ },
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(8.dp)
//                ) {
//                    Icon(
//                        imageVector = if (wiseSayingData.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                        contentDescription = "Favorite",
//                        tint = Color.Red
//                    )
//                }
//            }
//
//            Column (
//                Modifier
//                    .fillMaxWidth()
//                    .padding(18.dp)) {
//                Text(text = wiseSayingData.title, color = Color.Black,
//                    fontWeight = FontWeight.Bold, fontSize = 18.sp)
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(text = wiseSayingData.contents, color = Color.Black, maxLines = 3,
//                    fontSize = 14.sp)
//            }
//        }
//    }
}