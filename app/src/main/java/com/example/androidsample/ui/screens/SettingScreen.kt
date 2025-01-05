package com.example.androidsample.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidsample.ui.component.CustomSwitch
import com.example.androidsample.ui.component.CustomTopAppBar
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.viewmodel.WiseSayingViewModel

private var currentToast: Toast? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    wiseSayingViewModel: WiseSayingViewModel = hiltViewModel()) {

    AndroidSampleTheme {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = "설정",
                    onBackClick = { navController.popBackStack() }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                ProfileButtons(wiseSayingViewModel = wiseSayingViewModel)
                SettingButtons(wiseSayingViewModel = wiseSayingViewModel)
            }
        }
    }
}

@Composable
fun ProfileButtons(wiseSayingViewModel: WiseSayingViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder for profile image
                Icon(
                    imageVector = Icons.Default.Person, // Replace with an image loader like Coil for a real image
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.DarkGray
                )
            }

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "사용자님 안녕하세요!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SettingButtons(wiseSayingViewModel: WiseSayingViewModel) {
    val context = LocalContext.current
    val isDarkThemeEnabled by wiseSayingViewModel.getThemePreference().collectAsState(initial = false)
    val isPushNotificationEnabled by wiseSayingViewModel.getPushNotificationPreference().collectAsState(initial = false)

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
            CustomSwitch(
                checked = isPushNotificationEnabled,
                onCheckedChange = { isChecked ->
                    wiseSayingViewModel.savePushNotificationPreference(isChecked)
                    if (isChecked) {
//                        ShowCurrentToast(context = context, text = "명언 알림을 설정했어요.")
                    } else {
//                        ShowCurrentToast(context = context, text = "명언 알림을 해제했어요.")
                    }
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "다크 테마 활성화",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            CustomSwitch(
                checked = isDarkThemeEnabled,
                enabled = false,
                onCheckedChange = { isChecked ->
                    wiseSayingViewModel.saveThemePreference(isChecked)
                    Toast.makeText(context, "향후 업데이트 예정이니 기다려주세요.", Toast.LENGTH_SHORT).show()
                }
            )
        }

        ButtonWithIcon(
            text = "공지사항",
            icon = Icons.Default.Notifications,
            onClick = { Toast.makeText(context, "향후 업데이트 예정이니 기다려주세요.", Toast.LENGTH_SHORT).show() }
        )
        ButtonWithIcon(
            text = "별점 주기",
            icon = Icons.Default.Star,
            onClick = { openPlayStore(context) }
        )
        ButtonWithIcon(
            text = "문의 메일 보내기",
            icon = Icons.Default.Email,
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:threewave@kakao.com")
                }
                context.startActivity(intent)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // 화면 여백 추가
            verticalArrangement = Arrangement.Bottom, // 아래쪽에 배치
            horizontalAlignment = Alignment.End // 오른쪽 정렬
        ) {
            ClickableText(text = AnnotatedString("앱 버전 1.0.0"), onClick = {
                Toast.makeText(context, "향후 업데이트 예정이니 기다려주세요.", Toast.LENGTH_SHORT).show()
            })
        }
    }
}

fun ShowCurrentToast(context: Context, text: String) {
    currentToast?.cancel()
    currentToast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
    currentToast?.show()
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

fun openPlayStore(context: Context) {
    val appPackageName = context.packageName // 현재 앱의 패키지 이름
    try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
        )
    } catch (e: ActivityNotFoundException) {
        // Play Store가 설치되어 있지 않은 경우 웹 브라우저로 이동
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"))
        )
    }
}