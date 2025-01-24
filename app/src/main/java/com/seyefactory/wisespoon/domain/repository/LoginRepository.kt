package com.seyefactory.wisespoon.domain.repository

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginRepository @Inject constructor(@ApplicationContext private val context: Context) {
    fun createKakaoToken() {
        // 로그인 조합 예제
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                println("카카오톡 게정으로 로그인 실패")
                println(error.message)
                println(error.cause)
            } else if (token != null) {
                println("카카오톡 게정으로 로그인 성공 로직 추가 필요")
                println(token.idToken)
                println(token.accessToken)
                println(token.refreshToken)
                println(token.accessTokenExpiresAt)
                println(token.refreshTokenExpiresAt)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context = context) { token, error ->
                if (error != null) {
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    println("카카오톡 게정으로 로그인 성공 로직 추가 필요")
                }
            }
        } else {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    println("카카오톡 토큰 보기 실패")
                } else if (tokenInfo != null) {
                    println("카카오톡 토큰 보기 성공")
                    println(tokenInfo.id)
                    println(tokenInfo.appId)
                    println(tokenInfo.expiresIn)

                }
            }
            UserApiClient.instance.loginWithKakaoAccount(context = context, callback = callback)
        }
    }
}