package com.example.zjzapp.data.catalog

import com.example.zjzapp.data.model.ToolGroup
import com.example.zjzapp.data.model.ToolItem

/**
 * 功能目录。
 *
 * 按真实 App（免费证件照 v1.6.3）的功能地图整理：
 *  - 证件照类：证件照、身份证复印件、换底色、打印排版
 *  - 图片处理类：裁剪、缩放、擦除、合成、水印、放大、黑白上色
 *  - PDF / 文档类：生成PDF、PDF预览、文档扫描
 *
 * id 与 ui/navigation/Routes 中的功能路由一一对应。
 */
object FeatureCatalog {

    val IdPhoto = ToolItem("id_photo", "证件照", "一寸/二寸，标准尺寸一键制作", "id_photo")
    val IdCardCopy = ToolItem("id_card_copy", "身份证复印件", "正反面合成一张 A4", "id_card_copy")
    val ChangeBg = ToolItem("change_bg", "换底色", "蓝底/红底/白底一键切换", "change_bg")
    val PrintLayout = ToolItem("print_layout", "打印排版", "多张排版到 A4 打印", "print_layout")
    val Crop = ToolItem("crop", "图片裁剪", "自由比例与固定尺寸裁剪", "crop")
    val Resize = ToolItem("resize", "图片缩放", "按像素或比例调整尺寸", "resize")
    val Eraser = ToolItem("eraser", "图片擦除", "涂抹去掉多余杂物", "eraser")
    val Compose = ToolItem("compose", "照片合成", "多图拼接与画中画", "compose")
    val Watermark = ToolItem("watermark", "添加水印", "文字/图片水印，可平铺", "watermark")
    val Upscale = ToolItem("upscale", "照片放大", "无损放大，提升清晰度", "upscale")
    val Colorize = ToolItem("colorize", "黑白上色", "黑白照片一键变彩色", "colorize")
    val PdfCreate = ToolItem("pdf_create", "生成PDF", "图片合成 PDF，可加密", "pdf_create")
    val PdfViewer = ToolItem("pdf_viewer", "PDF预览", "打开并预览本地 PDF", "pdf_viewer")
    val DocScan = ToolItem("doc_scan", "文档扫描", "拍照去底，输出扫描件", "doc_scan")

    /** 首页「常用功能」九宫格（取高频入口）。 */
    val homeQuick: List<ToolItem> = listOf(
        IdPhoto, IdCardCopy, PrintLayout,
        PdfCreate, Watermark, Upscale
    )

    /** 工具页分组。 */
    val toolGroups: List<ToolGroup> = listOf(
        ToolGroup(
            "证件照",
            listOf(IdPhoto, IdCardCopy, ChangeBg, PrintLayout)
        ),
        ToolGroup(
            "图片处理",
            listOf(Crop, Resize, Eraser, Compose, Watermark, Upscale, Colorize)
        ),
        ToolGroup(
            "PDF 与文档",
            listOf(PdfCreate, PdfViewer, DocScan)
        )
    )

    val all: List<ToolItem> = toolGroups.flatMap { it.items }

    fun find(id: String?): ToolItem? = all.firstOrNull { it.id == id }
}
