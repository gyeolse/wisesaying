package com.seyefactory.wisespoon

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SampleApplication : Application() {
    companion object {
        private lateinit var instance: SampleApplication

        fun getInstance(): SampleApplication = instance

        val appContext: Context
            get() = instance.applicationContext
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
//        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }
}