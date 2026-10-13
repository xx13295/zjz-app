package com.example.zjzapp.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.BrandingWatermark
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * iconKey -> ImageVector 映射。
 *
 * data 层只存字符串 key，图标解析收敛在 UI 层。
 */
fun iconFor(key: String): ImageVector = when (key) {
    "id_photo" -> Icons.Filled.AccountBox
    "id_card_copy" -> Icons.Filled.Badge
    "change_bg" -> Icons.Filled.FormatColorFill
    "print_layout" -> Icons.Filled.Print
    "crop" -> Icons.Filled.Crop
    "resize" -> Icons.Filled.AspectRatio
    "eraser" -> Icons.Filled.AutoFixHigh
    "compose" -> Icons.Filled.GridView
    "watermark" -> Icons.AutoMirrored.Filled.BrandingWatermark
    "upscale" -> Icons.Filled.ZoomIn
    "colorize" -> Icons.Filled.ColorLens
    "pdf_create" -> Icons.Filled.PictureAsPdf
    "pdf_viewer" -> Icons.AutoMirrored.Filled.MenuBook
    "doc_scan" -> Icons.Filled.DocumentScanner
    else -> Icons.Filled.Build
}
