package com.heig.yfitops.ui.bottomsheet.expanded

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heig.yfitops.ui.bottomsheet.expanded.controls.PlayerControls
import com.heig.yfitops.ui.bottomsheet.expanded.top_section.TopSection
import com.heig.yfitops.ui.theme.bottomSheetThemeDark

/**
 * This component contains the player in its extended form,
 * it is divided into two parts a top section (TopSection)
 * and the controls for changing or stopping a song.
 */
@Composable
fun SheetExpanded() {
    Surface(
        color = bottomSheetThemeDark,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 40.dp)
            ) {
                TopSection()
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                PlayerControls()
            }
        }
    }
}
