package com.heig.yfitops.ui.bottomsheet.expanded

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heig.yfitops.ui.bottomsheet.expanded.top_section.TopSection
import com.heig.yfitops.ui.bottomsheet.expanded.controls.PlayerControls
import com.heig.yfitops.ui.theme.bottomSheetThemeDark

@Composable
fun SheetExpanded(
    name: String
) {
    Surface(
        color = bottomSheetThemeDark,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
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
