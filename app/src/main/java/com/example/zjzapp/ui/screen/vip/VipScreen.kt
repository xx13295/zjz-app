package com.example.zjzapp.ui.screen.vip

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.zjzapp.data.model.PayChannel
import com.example.zjzapp.data.model.VipPlan
import com.example.zjzapp.data.model.VipPlans
import com.example.zjzapp.data.repo.UserRepository
import com.example.zjzapp.ui.component.PageScaffold
import com.example.zjzapp.ui.component.SectionHeader

@Composable
fun VipScreen(onBack: () -> Unit) {
    var selected by remember { mutableStateOf(VipPlans.All.first { it.recommended }) }
    var channel by remember { mutableStateOf(PayChannel.Alipay) }

    PageScaffold(title = "会员中心", onBack = onBack) { modifier ->
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = "解锁全部高级功能",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "免广告 · 高清无水印导出 · 批量处理 · 全部证件尺寸",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                VipPlans.All.forEach { plan ->
                    PlanCard(
                        plan = plan,
                        selected = plan.id == selected.id,
                        onClick = { selected = plan },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            SectionHeader(title = "支付方式")
            PayRow(
                channel = PayChannel.Alipay,
                selected = channel == PayChannel.Alipay,
                onClick = { channel = PayChannel.Alipay }
            )
            PayRow(
                channel = PayChannel.Wechat,
                selected = channel == PayChannel.Wechat,
                onClick = { channel = PayChannel.Wechat }
            )

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    UserRepository.get().activateVip(selected.days)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("立即开通 ${selected.priceText}")
            }
        }
    }
}

@Composable
private fun PlanCard(
    plan: VipPlan,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.outlineVariant
    Surface(
        modifier = modifier
            .clickable(onClick = onClick)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            ),
        shape = RoundedCornerShape(14.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = plan.title, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(6.dp))
            Text(
                text = plan.priceText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "原价 ${plan.originalText}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PayRow(channel: PayChannel, selected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (channel == PayChannel.Alipay) Icons.Filled.AccountBalanceWallet
                else Icons.AutoMirrored.Filled.Chat,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = channel.label,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (selected) "已选择" else "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
