package com.example.zjzapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.zjzapp.ui.screen.about.AboutScreen
import com.example.zjzapp.ui.screen.auth.LoginScreen
import com.example.zjzapp.ui.screen.docs.MyDocsScreen
import com.example.zjzapp.ui.screen.feature.FeatureScreen
import com.example.zjzapp.ui.screen.home.HomeScreen
import com.example.zjzapp.ui.screen.profile.ProfileScreen
import com.example.zjzapp.ui.screen.settings.SettingsScreen
import com.example.zjzapp.ui.screen.tools.ToolsScreen
import com.example.zjzapp.ui.screen.vip.VipScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val openFeature: (String) -> Unit = { id ->
        navController.navigate(Routes.feature(id)) { launchSingleTop = true }
    }
    val back: () -> Unit = { navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        // ---- 底部 Tab ----
        composable(Routes.HOME) {
            HomeScreen(
                onOpenFeature = openFeature,
                onOpenDocs = { navController.navigate(Routes.MY_DOCS) }
            )
        }
        composable(Routes.TOOLS) {
            ToolsScreen(onOpenFeature = openFeature)
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onLogin = { navController.navigate(Routes.LOGIN) },
                onOpenVip = { navController.navigate(Routes.VIP) },
                onOpenDocs = { navController.navigate(Routes.MY_DOCS) },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                onOpenAbout = { navController.navigate(Routes.ABOUT) }
            )
        }

        // ---- 二级页 ----
        composable(Routes.LOGIN) { LoginScreen(onBack = back) }
        composable(Routes.VIP) { VipScreen(onBack = back) }
        composable(Routes.MY_DOCS) { MyDocsScreen(onBack = back) }
        composable(Routes.SETTINGS) {
            SettingsScreen(onBack = back, onOpenAbout = { navController.navigate(Routes.ABOUT) })
        }
        composable(Routes.ABOUT) { AboutScreen(onBack = back) }

        // ---- 功能页（统一走 feature/{featureId}）----
        composable(
            route = Routes.FEATURE,
            arguments = listOf(navArgument(Routes.ARG_FEATURE_ID) { type = NavType.StringType })
        ) { entry ->
            FeatureScreen(
                featureId = entry.arguments?.getString(Routes.ARG_FEATURE_ID),
                onBack = back
            )
        }
    }
}
