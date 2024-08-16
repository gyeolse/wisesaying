package com.example.androidsample.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidsample.MainActivity
import com.example.androidsample.R
import com.example.androidsample.ui.theme.AndroidSampleTheme
import com.example.androidsample.ui.theme.Purple40
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidSampleTheme {
                SplashScreen()
            }
        }
    }

    @Preview
    @Composable
    fun SplashScreen(
    ) {
        val alpha = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = Unit) {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(1000)
            )
            delay(2000L)

            Intent(this@SplashActivity, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.let { intent ->
                startActivity(intent)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Purple40),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val painter = painterResource(R.drawable.whatsapp)

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(93.dp)
                        .alpha(alpha.value),
                    painter = painter,
                    contentDescription = "Welluga Staff",
                )
            }

//            Image(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .alpha(alpha.value),
//                painter = painterResource(id = R.drawable.whatsapp),
////                painter = painterResource(id = R.drawable.splash_screen_bottom),
//                contentScale = ContentScale.FillWidth,
//                contentDescription = "Welluga Staff"
//            )
        }
    }
}