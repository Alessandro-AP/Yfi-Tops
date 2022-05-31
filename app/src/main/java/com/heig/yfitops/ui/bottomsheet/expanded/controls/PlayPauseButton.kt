package com.heig.yfitops.ui.bottomsheet.expanded.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.heig.yfitops.R
import com.heig.yfitops.ui.theme.bottomSheetThemeDark


@Composable
fun PlayPauseButton(onClick: () -> Unit) {
    CircleIconButtonLarge(
        onClick = onClick
    )
}

@Composable
fun CircleIconButtonLarge(
    onClick: () -> Unit
) {

    var playerState by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Color.White, CircleShape)
            .clickable(
                indication = rememberRipple(bounded = true),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { playerState = !playerState; onClick() }
            )
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            painter = if (playerState) painterResource(id = R.drawable.ic_baseline_pause_24)
            else painterResource(id = R.drawable.ic_baseline_play_arrow_24),
            tint = bottomSheetThemeDark,
            contentDescription = "Play Pause"
        )
    }
}