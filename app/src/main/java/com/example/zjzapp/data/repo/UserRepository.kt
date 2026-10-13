package com.example.zjzapp.data.repo

import com.example.zjzapp.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 用户仓库（内存实现）。
 *
 * 真实 App 走自有后端 + 短信验证码登录，这里先给出接口与内存实现，
 * 后续接 Retrofit/OkHttp 时替换实现即可，UI 不受影响。
 */
class UserRepository {

    private val _user = MutableStateFlow<UserProfile?>(null)
    val user: StateFlow<UserProfile?> = _user.asStateFlow()

    val isLoggedIn: Boolean get() = _user.value != null

    /** 模拟登录。 */
    fun login(phone: String, code: String) {
        _user.value = UserProfile(
            userId = "u_${phone.takeLast(4)}",
            nickname = "用户${phone.takeLast(4)}",
            phone = phone,
            isVip = false
        )
    }

    fun logout() {
        _user.value = null
    }

    /** 模拟开通 VIP。 */
    fun activateVip(days: Int) {
        val current = _user.value ?: return
        val expire = System.currentTimeMillis() + days * 24L * 60 * 60 * 1000
        _user.value = current.copy(isVip = true, vipExpireAt = expire)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        /** 进程内单例（骨架阶段先用简单单例，接入 DI 后可替换）。 */
        fun get(): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository().also { instance = it }
            }
    }
}
