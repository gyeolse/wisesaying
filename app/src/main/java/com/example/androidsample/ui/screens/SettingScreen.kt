package com.example.androidsample.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidsample.ui.theme.AndroidSampleTheme

@Composable
fun SettingScreen(navController: NavController) {
    AndroidSampleTheme {
        MyApp()
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(15.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    "SettingScreen",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier.padding(vertical = 20.dp)
//                )
//            }
//        }
    }
}



@Composable
fun MyApp() {
    var isReminderEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "매일 명언 알림", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "매일 9:00 AM에 습관 알림해드려요!", fontSize = 14.sp, color = Color.Gray)
            }
            Switch(
                checked = isReminderEnabled,
                onCheckedChange = { isReminderEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF2196F3),
                    uncheckedThumbColor = Color(0xFFB0BEC5)
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEDE7F6)) // Light purple background
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Setting Page",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ClickableText(text = AnnotatedString("공지사항"), onClick = { /* TODO */ })
            ClickableText(text = AnnotatedString("앱 버전 1.4.6"), onClick = { /* TODO */ })
            ClickableText(text = AnnotatedString("TEST"), onClick = { /* TODO */ })
            ClickableText(text = AnnotatedString("별점 주기"), onClick = { /* TODO */ })
            ClickableText(text = AnnotatedString("Instagram"), onClick = { /* TODO */ })
            ClickableText(text = AnnotatedString("만든 사람들"), onClick = { /* TODO */ })
            ClickableText(text = AnnotatedString("문의 메일 보내기 "), onClick = { /* TODO */ })
        }
    }
}