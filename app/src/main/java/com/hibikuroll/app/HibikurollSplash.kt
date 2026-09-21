package com.hibikuroll.app

import android.app.Activity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlinx.coroutines.delay

@Composable
fun HibikurollLaunchSplash(
    onFinished: () -> Unit
) {
    val context = LocalContext.current

    var phase by remember {
        mutableStateOf(0)
    }

    var pulse by remember {
        mutableStateOf(1f)
    }

    DisposableEffect(Unit) {
        val activity = context as? Activity

        val controller = activity?.let {
            WindowInsetsControllerCompat(
                it.window,
                it.window.decorView
            )
        }

        controller?.hide(
            WindowInsetsCompat.Type.systemBars()
        )

        onDispose {
            controller?.show(
                WindowInsetsCompat.Type.systemBars()
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(300)
        phase = 1

        delay(1200)
        phase = 2

        delay(1800)
        phase = 3

        delay(2500)
        phase = 4

        delay(700)
        onFinished()
    }

    LaunchedEffect(phase) {
        if (phase >= 3) {
            while (true) {
                pulse = 1.0f
                delay(850)

                pulse = 1.035f
                delay(850)
            }
        }
    }

    val logoScale by animateFloatAsState(
        targetValue = when {
            phase == 0 -> 0.55f
            phase >= 1 -> pulse
            else -> 0.55f
        },
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing
        ),
        label = "logoScale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = when {
            phase == 0 -> 0f
            phase >= 1 && phase < 4 -> 1f
            else -> 0f
        },
        animationSpec = tween(500),
        label = "logoAlpha"
    )

    val nameAlpha by animateFloatAsState(
        targetValue = if (phase >= 2 && phase < 4) 1f else 0f,
        animationSpec = tween(700),
        label = "nameAlpha"
    )

    val versionAlpha by animateFloatAsState(
        targetValue = if (phase >= 3 && phase < 4) 1f else 0f,
        animationSpec = tween(700),
        label = "versionAlpha"
    )

    val screenAlpha by animateFloatAsState(
        targetValue = if (phase >= 4) 0f else 1f,
        animationSpec = tween(550),
        label = "screenAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .graphicsLayer {
                alpha = screenAlpha
            },
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.hibikuroll_logo
                ),
                contentDescription = "Hibikuroll",
                modifier = Modifier
                    .size(260.dp)
                    .graphicsLayer {
                        alpha = logoAlpha
                        scaleX = logoScale
                        scaleY = logoScale
                        rotationZ =
                            if (phase >= 1) 0f
                            else -6f
                    }
            )

            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.size(22.dp)
            )

            androidx.compose.material3.Text(
                text = "Hibikuroll",
                color = Color(0xFFFFC928),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.graphicsLayer {
                    alpha = nameAlpha
                    translationY =
                        if (nameAlpha == 1f) 0f else 18f
                }
            )
        }

        androidx.compose.material3.Text(
            text = "v1.1.1 ( 10 )",
            color = Color(0xFFFFC928),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 42.dp)
                .graphicsLayer {
                    alpha = versionAlpha
                    translationY =
                        if (versionAlpha == 1f) 0f else 14f
                }
        )
    }
}
