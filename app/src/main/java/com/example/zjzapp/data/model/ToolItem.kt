package com.example.zjzapp.data.model

/**
 * 功能条目。
 *
 * [iconKey] 由 UI 层解析为具体图标，避免 data 层依赖 Compose。
 * 导航路由由 id 推导：`Routes.feature(item.id)`。
 */
data class ToolItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconKey: String
)

/** 工具分组（工具页按分类展示）。 */
data class ToolGroup(
    val title: String,
    val items: List<ToolItem>
)
