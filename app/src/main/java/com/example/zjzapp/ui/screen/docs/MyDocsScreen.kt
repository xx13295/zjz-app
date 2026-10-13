package com.example.zjzapp.ui.screen.docs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.zjzapp.data.repo.DocRepository
import com.example.zjzapp.ui.component.AppListItem
import com.example.zjzapp.ui.component.EmptyState
import com.example.zjzapp.ui.component.PageScaffold
import com.example.zjzapp.ui.component.iconFor

@Composable
fun MyDocsScreen(onBack: () -> Unit) {
    val docs by DocRepository.get().docs.collectAsState()

    PageScaffold(title = "我的文档", onBack = onBack) { modifier ->
        Column(modifier = modifier.fillMaxSize()) {
            Text(
                text = "共 ${docs.size} 个文件",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            if (docs.isEmpty()) {
                EmptyState(
                    icon = Icons.Filled.FolderOpen,
                    title = "还没有文档",
                    description = "使用证件照、PDF 工具生成的文件会出现在这里"
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(docs, key = { it.id }) { doc ->
                        AppListItem(
                            icon = iconFor(
                                when (doc.type.name) {
                                    "Pdf" -> "pdf_viewer"
                                    "Scan" -> "doc_scan"
                                    else -> "id_photo"
                                }
                            ),
                            title = doc.name,
                            subtitle = "${doc.type.label} · ${doc.sizeText}" +
                                if (doc.pageCount > 1) " · ${doc.pageCount} 页" else "",
                            onClick = {},
                            trailing = {
                                IconButton(onClick = { DocRepository.get().remove(doc.id) }) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "删除",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
