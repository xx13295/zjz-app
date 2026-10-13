package com.example.zjzapp.data.model

/** 用户信息（登录后）。 */
data class UserProfile(
    val userId: String,
    val nickname: String,
    val phone: String,
    val avatarUrl: String? = null,
    val isVip: Boolean = false,
    /** VIP 到期时间（毫秒），0 表示未开通 */
    val vipExpireAt: Long = 0L
) {
    val maskedPhone: String
        get() = if (phone.length == 11) "${phone.take(3)}****${phone.takeLast(4)}" else phone
}
