package com.example.zjzapp.data.model

/** 文档类型。 */
enum class DocType(val label: String) {
    Photo("照片"),
    Pdf("PDF"),
    Scan("扫描件")
}

/** 「我的文档」中的一条记录。 */
data class DocItem(
    val id: String,
    val name: String,
    val type: DocType,
    val sizeBytes: Long,
    val createdAt: Long,
    val pageCount: Int = 1
) {
    val sizeText: String
        get() = when {
            sizeBytes >= 1024 * 1024 -> "%.1f MB".format(sizeBytes / 1024.0 / 1024.0)
            sizeBytes >= 1024 -> "%.0f KB".format(sizeBytes / 1024.0)
            else -> "$sizeBytes B"
        }
}
