package com.maiky.bitacora.ui.screen.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.maiky.bitacora.domain.usecase.DayStatistics
import com.maiky.bitacora.ui.theme.CompletedGreen

@Composable
fun StatsBar(
    statistics: DayStatistics,
    modifier: Modifier = Modifier
) {
    if (statistics.totalActivities == 0) return

    val progress by animateFloatAsState(
        targetValue = statistics.completionPercentage / 100f,
        animationSpec = tween(600),
        label = "progressAnim"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${statistics.completedActivities}/${statistics.totalActivities} completadas",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${statistics.completionPercentage.toInt()}%",
                style = MaterialTheme.typography.labelMedium,
                color = CompletedGreen
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(MaterialTheme.shapes.small),
            color = CompletedGreen,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}
