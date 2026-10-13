package com.example.zjzapp.data.repo

import com.example.zjzapp.data.model.DocItem
import com.example.zjzapp.data.model.DocType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 文档仓库（内存实现）。
 *
 * 真实 App 的「我的文档」会扫描本地存储（含 ScopedStorage 适配），
 * 这里先用内存样例数据驱动 UI。
 */
class DocRepository {

    private val _docs = MutableStateFlow(sample())
    val docs: StateFlow<List<DocItem>> = _docs.asStateFlow()

    fun remove(id: String) {
        _docs.value = _docs.value.filterNot { it.id == id }
    }

    fun rename(id: String, newName: String) {
        _docs.value = _docs.value.map { if (it.id == id) it.copy(name = newName) else it }
    }

    private fun sample(): List<DocItem> {
        val now = System.currentTimeMillis()
        return listOf(
            DocItem("d1", "一寸证件照_蓝底.jpg", DocType.Photo, 184_320, now - 3_600_000),
            DocItem("d2", "身份证复印件.pdf", DocType.Pdf, 2_310_144, now - 86_400_000, pageCount = 1),
            DocItem("d3", "合同扫描件.pdf", DocType.Scan, 5_874_944, now - 172_800_000, pageCount = 6),
            DocItem("d4", "简历照片_白底.jpg", DocType.Photo, 96_256, now - 259_200_000)
        )
    }

    companion object {
        @Volatile
        private var instance: DocRepository? = null

        fun get(): DocRepository =
            instance ?: synchronized(this) {
                instance ?: DocRepository().also { instance = it }
            }
    }
}
