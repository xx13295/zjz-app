package com.example.zjzapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.ui.graphics.vector.ImageVector

/** 底部导航的三个 Tab。 */
enum class TabDest(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    Home(Routes.HOME, "首页", Icons.Filled.Home, Icons.Outlined.Home),
    Tools(Routes.TOOLS, "工具", Icons.Filled.Widgets, Icons.Outlined.Widgets),
    Profile(Routes.PROFILE, "我的", Icons.Filled.Person, Icons.Outlined.Person);

    companion object {
        fun isTab(route: String?): Boolean = entries.any { it.route == route }
    }
}
