package com.example.androidsample

import android.content.pm.PackageManager
import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.androidsample.domain.repository.WiseSayingDataRepository
import com.example.androidsample.receiver.AlarmScheduler
import com.example.androidsample.ui.navigation.BottomNavigationBar
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.viewmodel.WiseSayingViewModel
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val wiseSayingViewModel: WiseSayingViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [TODO] 앱 처음 켰을 때, 권한 설정 로직 추가
        MobileAds.initialize(this) { }

        val intent = intent
        handleCurrentIntent(intent)

        wiseSayingViewModel.checkAndSyncNotificationSettings()

        installSplashScreen()
        setContent {
            AndroidSampleTheme {
                BottomNavigationBar()
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
            }
        }
    }

    private fun handleCurrentIntent(intent: Intent) {
        val requestPermission = intent.getBooleanExtra("request_permission", false)
        Log.d("MainActivity", "Request Permission Intent Flag: $requestPermission")

        if (requestPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionStatus = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
            Log.d("MainActivity", "Permission Status: $permissionStatus")

            if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Log.d("MainActivity", "Permission permanently denied")
                    AlertDialog.Builder(this)
                        .setTitle("알람 설정 필요")
                        .setMessage("알람을 설정하기 위해서는 설정에 가서 직접 알람 설정을 켜주세요.")
                        .setPositiveButton("설정으로 이동") { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", packageName, null)
                            }
                            startActivity(intent)
                        }
                        .setNegativeButton("취소", null)
                        .show()
                } else {
                    Log.d("MainActivity", "Requesting POST_NOTIFICATIONS permission")
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
                }
            } else {
                Log.d("MainActivity", "Permission already granted")
            }
        } else {
            Log.d("MainActivity", "Request Permission not required or OS version not eligible")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidSampleTheme {
        Greeting("Android")
    }
}