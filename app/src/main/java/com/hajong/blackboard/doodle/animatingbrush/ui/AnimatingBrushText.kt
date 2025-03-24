package com.hajong.blackboard.doodle.animatingbrush.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AnimatingBrushText(
    text: String,
    type: Int,
    colorSet: Pair<Long, Long>,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 50.sp,
) {
    val brush = when (type) {
        1 -> candyCaneAnimation(fontSize, colorSet)
        2 -> rockingAnimation(colorSet)
        3 -> waveAnimation(colorSet)
        else -> candyCaneAnimation(fontSize, colorSet)
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = text,
            style = TextStyle(
                brush = brush,
                fontSize = fontSize,
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}

@Composable
private fun candyCaneAnimation(
    fontSize: TextUnit,
    colorSet: Pair<Long, Long>,
): Brush {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val currentFontSizePx = with(LocalDensity.current) {
        fontSize.toPx()
    }
    val gradient = listOf(Color(colorSet.first), Color(colorSet.second))

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = currentFontSizePx * 2,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing)),
        label = ""
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(offset, offset),
        end = Offset(offset + currentFontSizePx, offset + currentFontSizePx),
        tileMode = TileMode.Mirror
    )

    return brush
}

@Composable
private fun rockingAnimation(
    colorSet: Pair<Long, Long>,
): Brush {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = ""
    )

    val gradient = listOf(Color(colorSet.first), Color(colorSet.second))

    val brush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val widthOffset = size.width * offset
                val heightOffset = size.height * offset
                return LinearGradientShader(
                    colors = gradient,
                    from = Offset(widthOffset, heightOffset),
                    to = Offset(widthOffset + size.width, heightOffset + size.height),
                    tileMode = TileMode.Mirror
                )
            }
        }
    }
    return brush
}

@Composable
private fun waveAnimation(
    colorSet: Pair<Long, Long>,
): Brush {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val gradient = listOf(
        Color(colorSet.second),
        Color(colorSet.second),
        Color(colorSet.first),
        Color(colorSet.second),
    )

    val offset by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing)
        ),
        label = ""
    )

    val brush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val width = size.width
                val height = size.height
                val radius = (offset * maxOf(width, height)) / 2
                return RadialGradientShader(
                    colors = gradient,
                    center = Offset(width / 2f, height / 2f),
                    radius = radius,
                    tileMode = TileMode.Clamp
                )
            }
        }
    }
    return brush
}

@Preview(showBackground = true)
@Composable
private fun ABTPreview() {
    AnimatingBrushText(
        text = "컨트롤러의 커맨드를 받아 모델이 \n자신을 변경하면 Dependents에 \nchanged: 메시지를 발송한다.",
        type = 3,
        colorSet = (0xFFF74B98 to 0xFF47DAFF),
    )
}