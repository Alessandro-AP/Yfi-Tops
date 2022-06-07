package com.heig.yfitops.ui.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.heig.yfitops.R
import com.heig.yfitops.ui.navigation.Screen

import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.8f,         /** Scale ratio */
            animationSpec = tween(      /** Duration of animation */
                durationMillis = 500,
                easing = {              /** Fraction of animation */
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000)
        navController.popBackStack()
        navController.navigate(Screen.PlaylistScreen.route)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.splash_screen_logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }

}