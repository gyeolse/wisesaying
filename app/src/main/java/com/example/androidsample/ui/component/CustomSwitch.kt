package com.example.androidsample.ui.component

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    thumbColor: Color = Color.Black,
    trackColor: Color = Color.Gray,
    uncheckedThumbColor: Color = Color.Black,
    uncheckedTrackColor: Color = Color.LightGray,
    enabled: Boolean = true // 기본값은 활성화 상태
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = thumbColor,  // 활성화 상태의 썸 색상
            checkedTrackColor = trackColor,  // 활성화 상태의 트랙 색상
            uncheckedThumbColor = uncheckedThumbColor,  // 비활성화 상태의 썸 색상
            uncheckedTrackColor = uncheckedTrackColor  // 비활성화 상태의 트랙 색상
        )
    )
}