package com.heig.yfitops.ui.bottomsheet

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.ExperimentalMaterialApi

/**
 * BottomSheetScaffold state
 *
 *  1.0f - Expanded
 *  0.0f - Collapsed
 */
@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentFraction: Float
    /**
     * Please note: The BottomSheet component sometimes does not function as it should,
     * if the component is not perfectly loaded when opened, an exception is thrown.
     * Other people have the same problem but nobody has proposed a solution.
     * We therefore decided to use this approach to temporarily solve the problem.
     */
    get() {
        var fraction = 0f
        var targetValue : BottomSheetValue = Collapsed
        var currentValue : BottomSheetValue = Collapsed
        try {
            fraction = bottomSheetState.progress.fraction
            targetValue = bottomSheetState.targetValue
            currentValue = bottomSheetState.currentValue
        }catch (e: Exception ){}
        finally {
            return when {
                currentValue == Collapsed && targetValue == Collapsed -> 0f
                currentValue == Expanded && targetValue == Expanded -> 1f
                currentValue == Collapsed && targetValue == Expanded -> fraction
                else -> 1f - fraction
            }
        }
    }
