package com.example.zjzapp.ui.navigation

/**
 * 全局路由表。
 *
 * 功能页统一走 `feature/{id}`，id 取自 [com.example.zjzapp.data.catalog.FeatureCatalog]。
 */
object Routes {
    const val HOME = "home"
    const val TOOLS = "tools"
    const val PROFILE = "profile"

    const val LOGIN = "login"
    const val VIP = "vip"
    const val MY_DOCS = "my_docs"
    const val SETTINGS = "settings"
    const val ABOUT = "about"

    const val ARG_FEATURE_ID = "featureId"
    private const val FEATURE_PREFIX = "feature"

    const val FEATURE = "$FEATURE_PREFIX/{$ARG_FEATURE_ID}"

    fun feature(id: String): String = "$FEATURE_PREFIX/$id"
}
