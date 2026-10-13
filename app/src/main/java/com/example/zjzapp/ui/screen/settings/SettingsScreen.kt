package com.example.zjzapp.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zjzapp.ui.component.AppListItem
import com.example.zjzapp.ui.component.PageScaffold
import com.example.zjzapp.ui.component.SectionHeader

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenAbout: () -> Unit
) {
    var wifiOnly by remember { mutableStateOf(true) }
    var saveOriginal by remember { mutableStateOf(false) }

    PageScaffold(title = "设置", onBack = onBack) { modifier ->
        Column(modifier = modifier) {
            SectionHeader(title = "下载与保存")
            SwitchRow(
                title = "仅 Wi-Fi 下载",
                subtitle = "避免消耗移动数据",
                checked = wifiOnly,
                onCheckedChange = { wifiOnly = it }
            )
            SwitchRow(
                title = "保存原图",
                subtitle = "处理完成后保留原始照片",
                checked = saveOriginal,
                onCheckedChange = { saveOriginal = it }
            )

            SectionHeader(title = "关于")
            AppListItem(
                icon = Icons.Filled.Info,
                title = "关于本应用",
                subtitle = "版本、开源许可",
                onClick = onOpenAbout
            )
        }
    }
}

@Composable
private fun SwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    AppListItem(
        icon = if (title.contains("Wi-Fi")) Icons.Filled.Wifi else Icons.Filled.Save,
        title = title,
        subtitle = subtitle,
        onClick = { onCheckedChange(!checked) },
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    )
}
