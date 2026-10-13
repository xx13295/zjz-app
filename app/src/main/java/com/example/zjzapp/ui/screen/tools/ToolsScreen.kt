package com.example.zjzapp.ui.screen.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.zjzapp.data.catalog.FeatureCatalog
import com.example.zjzapp.ui.component.FeatureRow
import com.example.zjzapp.ui.component.SectionHeader

@Composable
fun ToolsScreen(onOpenFeature: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Text(
                text = "全部工具",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)
            )
        }
        FeatureCatalog.toolGroups.forEach { group ->
            item(key = "h_${group.title}") { SectionHeader(title = group.title) }
            items(group.items.size, key = { i -> group.items[i].id }) { i ->
                FeatureRow(
                    item = group.items[i],
                    onClick = { onOpenFeature(group.items[i].id) }
                )
            }
        }
    }
}
