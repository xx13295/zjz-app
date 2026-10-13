package com.example.zjzapp.data.model

/** VIP 套餐。 */
data class VipPlan(
    val id: String,
    val title: String,
    val priceYuan: Double,
    val originalYuan: Double,
    val days: Int,
    val recommended: Boolean = false
) {
    val priceText: String get() = "¥%.2f".format(priceYuan)
    val originalText: String get() = "¥%.2f".format(originalYuan)
}

object VipPlans {
    val All: List<VipPlan> = listOf(
        VipPlan("month", "连续包月", 18.0, 30.0, 30),
        VipPlan("quarter", "季度会员", 45.0, 90.0, 90, recommended = true),
        VipPlan("year", "年度会员", 128.0, 360.0, 365)
    )
}

/** 支付渠道。 */
enum class PayChannel(val label: String) {
    Alipay("支付宝"),
    Wechat("微信支付")
}
