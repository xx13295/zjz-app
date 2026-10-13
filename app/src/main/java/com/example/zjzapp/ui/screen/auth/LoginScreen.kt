package com.example.zjzapp.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.zjzapp.data.repo.UserRepository
import com.example.zjzapp.ui.component.PageScaffold

@Composable
fun LoginScreen(onBack: () -> Unit) {
    var phone by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var agreed by remember { mutableStateOf(false) }

    val canSendCode = phone.length == 11
    val canLogin = canSendCode && code.isNotBlank() && agreed

    PageScaffold(title = "登录", onBack = onBack) { modifier ->
        Column(
            modifier = modifier.padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "手机号登录",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "未注册的手机号将自动创建账号",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { if (it.length <= 11) phone = it.filter(Char::isDigit) },
                label = { Text("手机号") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { if (it.length <= 6) code = it.filter(Char::isDigit) },
                    label = { Text("验证码") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(12.dp))
                OutlinedButton(onClick = { /* TODO: 接入短信验证码接口 */ }, enabled = canSendCode) {
                    Text("获取验证码")
                }
            }
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = agreed, onCheckedChange = { agreed = it })
                Text(
                    text = "我已阅读并同意《用户协议》和《隐私政策》",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    UserRepository.get().login(phone, code)
                    onBack()
                },
                enabled = canLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("登录 / 注册")
            }
        }
    }
}
