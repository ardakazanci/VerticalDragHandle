package com.ardakazanci.verticaldraghandle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.ardakazanci.verticaldraghandle.ui.theme.VerticalDragHandleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VerticalDragHandleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SliderControl(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SliderControl(modifier: Modifier = Modifier) {
    var valueLevel by remember { mutableIntStateOf(50) }
    val isMaxValue = valueLevel == 100

    val stretchScale by animateFloatAsState(
        targetValue = if (isMaxValue) 1.3f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = 0.5f)
    )

    val bounceScale by animateFloatAsState(
        targetValue = if (isMaxValue) 1.1f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium, dampingRatio = 0.8f)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "$valueLevel%",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(24.dp))


            Row {
                Slider(
                    currentValue = valueLevel,
                    stretchScale = stretchScale,
                    bounceScale = bounceScale,
                    onValueChange = { delta ->
                        valueLevel = (valueLevel + delta).coerceIn(0, 100)
                    },
                )

                Slider(
                    currentValue = valueLevel,
                    stretchScale = stretchScale,
                    bounceScale = bounceScale,
                    onValueChange = { delta ->
                        valueLevel = (valueLevel + delta).coerceIn(0, 100)
                    },
                    sliderColors = listOf(Color(0xFFFFA726), Color(0xFFFF7043)),
                    handleColors = listOf(
                        Color(0xFFFFD54F),
                        Color(0xFFFFA726)
                    )
                )

                Slider(
                    currentValue = valueLevel,
                    stretchScale = stretchScale,
                    bounceScale = bounceScale,
                    onValueChange = { delta ->
                        valueLevel = (valueLevel + delta).coerceIn(0, 100)
                    },
                    sliderColors = listOf(
                        Color(0xFFFFCDD2),
                        Color(0xFFEF5350)
                    ),
                    handleColors = listOf(
                        Color(0xFFFF5252),
                        Color(0xFFD32F2F)
                    )
                )

                Slider(
                    currentValue = valueLevel,
                    stretchScale = stretchScale,
                    bounceScale = bounceScale,
                    onValueChange = { delta ->
                        valueLevel = (valueLevel + delta).coerceIn(0, 100)
                    },
                    sliderColors = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue),
                    handleColors = listOf(Color.Cyan, Color.Magenta)
                )

            }
        }
    }
}

@Composable
fun Slider(
    currentValue: Int,
    stretchScale: Float,
    bounceScale: Float,
    onValueChange: (Int) -> Unit,
    sliderColors: List<Color> = listOf(Color(0xFF76C7C0), Color(0xFF50B8B3)),
    handleColors: List<Color> = listOf(
        Color(0xFF76C7C0),
        Color(0xFF50B8B3)
    )
) {
    Box(
        modifier = Modifier
            .height(200.dp * stretchScale)
            .width(60.dp * bounceScale)
            .background(
                color = Color(0xFFEFEFEF),
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(currentValue / 100f)
                .clip(MaterialTheme.shapes.medium)
                .background(
                    brush = Brush.verticalGradient(
                        colors = sliderColors
                    )
                )
        )


        if (currentValue == 100) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0x88FFFFFF))
                        )
                    )
            )
        }


        Box(
            modifier = Modifier
                .size(40.dp * bounceScale)
                .clip(CircleShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = handleColors
                    )
                )
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val delta = (dragAmount / 5f).y.toInt()
                        onValueChange(-delta)
                    }
                }
        )
    }
}





