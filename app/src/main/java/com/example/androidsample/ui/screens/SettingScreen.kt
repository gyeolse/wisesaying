package com.example.androidsample.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidsample.ui.theme.AndroidSampleTheme

@Composable
fun SettingScreen(navController: NavController) {
    AndroidSampleTheme {
        SampleApp()
//        MyApp()
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

        Button(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .width(246.dp),
            onClick = { /*TODO*/ }) {
            Text(text = "공지사항", style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ), textAlign = TextAlign.Center)
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

@Composable
fun SampleApp() {
    val context = LocalContext.current
    var isPushNotificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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
                checked = isPushNotificationsEnabled,
                onCheckedChange = { isPushNotificationsEnabled = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF2196F3),
                    uncheckedThumbColor = Color(0xFFB0BEC5)
                )
            )
        }

        ButtonWithIcon(
            text = "공지사항",
            icon = Icons.Default.Notifications,
            onClick = { /* Handle Notice click */ }
        )
        ButtonWithIcon(
            text = "별점 주기",
            icon = Icons.Default.Star,
            onClick = { /* Handle Rating click */ }
        )
        ButtonWithIcon(
            text = "문의 메일 보내기",
            icon = Icons.Default.Email,
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:example@example.com")
                }
                context.startActivity(intent)
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Push 알림", fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Switch(
                checked = isPushNotificationsEnabled,
                onCheckedChange = { isChecked -> isPushNotificationsEnabled = isChecked }
            )
        }
    }
}

@Composable
fun ButtonWithIcon(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null, tint = Color.White)
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}