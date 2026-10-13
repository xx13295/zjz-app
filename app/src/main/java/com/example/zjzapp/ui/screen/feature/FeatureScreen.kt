package com.example.zjzapp.ui.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.zjzapp.data.catalog.FeatureCatalog
import com.example.zjzapp.data.model.PhotoBackground
import com.example.zjzapp.data.model.PhotoSpecs
import com.example.zjzapp.data.model.ToolItem
import com.example.zjzapp.ui.component.EmptyState
import com.example.zjzapp.ui.component.PageScaffold
import com.example.zjzapp.ui.component.SectionHeader
import com.example.zjzapp.ui.component.iconFor

/**
 * 功能页统一入口。
 *
 * 证件照 / 换底色 走「尺寸 + 底色」选择；其余功能走「操作步骤」说明。
 * 具体业务实现（相机、裁剪、抠图、PDF 引擎）后续接入，这里保留完整交互骨架。
 */
@Composable
fun FeatureScreen(featureId: String?, onBack: () -> Unit) {
    val item = FeatureCatalog.find(featureId)
    if (item == null) {
        PageScaffold(title = "功能", onBack = onBack) { modifier ->
            EmptyState(
                icon = Icons.Filled.Build,
                title = "功能不存在",
                description = "路由参数 featureId=$featureId 未在功能目录中注册",
                modifier = modifier
            )
        }
        return
    }

    PageScaffold(title = item.title, onBack = onBack) { modifier ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            FeatureHeader(item)
            Spacer(Modifier.height(20.dp))

            when (item.id) {
                "id_photo" -> PhotoSpecSection(showBackground = true)
                "change_bg" -> PhotoSpecSection(showBackground = true, showSize = false)
                else -> StepsSection(item.id)
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { /* TODO: 接入具体处理流程（相机 / 选图 / 引擎） */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(primaryAction(item.id))
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = tipOf(item.id),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun FeatureHeader(item: ToolItem) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = iconFor(item.iconKey),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PhotoSpecSection(showBackground: Boolean, showSize: Boolean = true) {
    var selectedSpec by remember { mutableStateOf(PhotoSpecs.All.first()) }
    var selectedBg by remember { mutableStateOf(PhotoBackground.Blue) }

    if (showSize) {
        SectionHeader(title = "尺寸规格")
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PhotoSpecs.All.forEach { spec ->
                FilterChip(
                    selected = spec.id == selectedSpec.id,
                    onClick = { selectedSpec = spec },
                    label = { Text("${spec.name} ${spec.widthMm}×${spec.heightMm}mm") }
                )
            }
        }
    }

    if (showBackground) {
        SectionHeader(title = "背景颜色")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PhotoBackground.entries.forEach { bg ->
                val selected = bg == selectedBg
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { selectedBg = bg }
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(bg.argb))
                            .border(
                                width = if (selected) 3.dp else 1.dp,
                                color = if (selected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outlineVariant,
                                shape = CircleShape
                            )
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = bg.label,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun StepsSection(id: String) {
    SectionHeader(title = "操作步骤")
    val steps = stepsOf(id)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        steps.forEachIndexed { index, step ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Text(text = step, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

private fun stepsOf(id: String): List<String> = when (id) {
    "id_card_copy" -> listOf("拍摄身份证正面", "拍摄身份证反面", "自动合成到 A4 尺寸", "保存或直接打印")
    "print_layout" -> listOf("选择已制作的证件照", "设置每张 A4 的排版数量", "预览排版效果", "保存并打印")
    "crop" -> listOf("选择照片", "拖动边框选择裁剪区域", "选择固定比例或自由裁剪", "确认保存")
    "resize" -> listOf("选择照片", "输入目标像素或缩放比例", "选择是否保持宽高比", "导出新尺寸图片")
    "eraser" -> listOf("选择照片", "调整画笔大小涂抹要擦除的区域", "可撤销上一步操作", "保存结果")
    "compose" -> listOf("选择多张照片", "选择拼接方向与间距", "调整顺序与边框", "合成并保存")
    "watermark" -> listOf("选择照片", "输入水印文字或选择图片水印", "调整位置、大小与透明度", "保存带水印图片")
    "upscale" -> listOf("选择照片", "选择放大倍数", "等待无损放大处理", "对比效果并保存")
    "colorize" -> listOf("选择黑白照片（长宽比 3:1 以内）", "点击一键上色", "预览上色效果", "保存彩色照片")
    "pdf_create" -> listOf("选择一张或多张图片", "调整顺序与页面尺寸", "可选设置密码保护", "生成 PDF 文件")
    "pdf_viewer" -> listOf("从「我的文档」选择 PDF", "支持翻页与缩放预览", "可分享或导出到手机", "支持打印")
    "doc_scan" -> listOf("用相机拍摄文档", "自动识别边缘并去底", "支持多页连续扫描", "导出为 PDF 或图片")
    else -> listOf("选择照片", "调整参数", "预览效果", "保存结果")
}

private fun primaryAction(id: String): String = when (id) {
    "id_photo" -> "拍照 / 选择照片"
    "id_card_copy" -> "开始拍摄身份证"
    "change_bg" -> "选择照片换底色"
    "print_layout" -> "选择照片排版"
    "crop" -> "选择照片裁剪"
    "resize" -> "选择照片调整尺寸"
    "eraser" -> "选择照片擦除"
    "compose" -> "选择照片合成"
    "watermark" -> "选择照片加水印"
    "upscale" -> "选择照片放大"
    "colorize" -> "选择黑白照片上色"
    "pdf_create" -> "选择图片生成 PDF"
    "pdf_viewer" -> "打开本地 PDF"
    "doc_scan" -> "开始扫描文档"
    else -> "开始"
}

private fun tipOf(id: String): String = when (id) {
    "change_bg" -> "提示：换底色仅针对纯色背景证件照，其他照片暂不支持"
    "colorize" -> "提示：请选择长宽比 3:1 以内的黑白照片，效果更佳"
    "pdf_create" -> "提示：生成的 PDF 会保存到「我的文档」"
    "doc_scan" -> "提示：拍摄时保持光线均匀，边缘识别更准确"
    else -> "提示：处理结果会保存到「我的文档」，可在「我的」中查看"
}
