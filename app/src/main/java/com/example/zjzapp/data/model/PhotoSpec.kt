package com.example.zjzapp.data.model

/**
 * 证件照规格。
 *
 * 对应真实 App（免费证件照 v1.6.3）中「证件照」功能的尺寸预设：
 * 一寸 / 二寸 / 小一寸 / 小二寸 / 大一寸 / 护照 / 签证 等。
 */
data class PhotoSpec(
    val id: String,
    val name: String,
    /** 打印尺寸（毫米） */
    val widthMm: Int,
    val heightMm: Int,
    /** 电子照像素（300dpi 换算） */
    val pixelW: Int,
    val pixelH: Int,
    val backgrounds: List<PhotoBackground> = PhotoBackground.defaults
)

/** 证件照底色。 */
enum class PhotoBackground(val label: String, val argb: Long) {
    Blue("蓝底", 0xFF438EDB),
    Red("红底", 0xFFD9001B),
    White("白底", 0xFFFFFFFF),
    Gray("灰底", 0xFFB8B8B8),
    DeepBlue("深蓝底", 0xFF2A5CAA);

    companion object {
        val defaults: List<PhotoBackground> = listOf(Blue, Red, White)
    }
}

/** 内置规格预设。 */
object PhotoSpecs {
    val All: List<PhotoSpec> = listOf(
        PhotoSpec("one_inch", "一寸", 25, 35, 295, 413),
        PhotoSpec("two_inch", "二寸", 35, 49, 413, 579),
        PhotoSpec("small_one", "小一寸", 22, 32, 260, 378),
        PhotoSpec("small_two", "小二寸", 35, 45, 413, 531),
        PhotoSpec("big_one", "大一寸", 33, 48, 390, 567),
        PhotoSpec("passport", "护照", 33, 48, 390, 567),
        PhotoSpec("visa", "签证", 35, 45, 413, 531, listOf(PhotoBackground.White))
    )
}
