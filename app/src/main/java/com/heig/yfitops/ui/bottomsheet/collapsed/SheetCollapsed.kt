package com.heig.yfitops.ui.bottomsheet.collapsed

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heig.yfitops.R
import com.heig.yfitops.ui.bottomsheet.collapsed.modifier.noRippleClickable
import com.heig.yfitops.ui.theme.bottomSheetThemeDark
import com.heig.yfitops.viewmodels.MainViewModel
import com.heig.yfitops.viewmodels.MainViewModelFactory

@Composable
fun SheetCollapsed(
    name: String,
    isCollapsed: Boolean,
    currentFraction: Float,
    onSheetClick: () -> Unit
) {
    val context = LocalContext.current
    val mainViewModel : MainViewModel = viewModel(factory = MainViewModelFactory(LocalContext.current))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(bottomSheetThemeDark)
            .graphicsLayer(alpha = 1f - (currentFraction * 3.5f)) // Hide collapsed on scrolling
            .noRippleClickable(
                onClick = onSheetClick,
                enabled = isCollapsed
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = { },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                tint = Color.White,
                contentDescription = "Play Pause"
            )
        }

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = name,
            color = Color.White,
            style = MaterialTheme.typography.caption
        )

        IconButton(
            onClick = {
                Toast.makeText(
                context,
                "Feature not available yet",
                Toast.LENGTH_SHORT
            ).show() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
                tint = Color.White,
                contentDescription = "Favorite"
            )
        }

        IconButton(
            onClick = { mainViewModel.skipToNextSong() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_baseline_skip_next_24),
                tint = Color.White,
                contentDescription = "Skip Next"
            )
        }
    }
}