package com.ssaisriharsha.captaingame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssaisriharsha.captaingame.ui.theme.CaptainGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaptainGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val c = innerPadding
                    CaptainGame()
                }
            }
        }
    }
}

@Composable
fun CaptainGame() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // State Variables
        val treasuresFound = remember {
            mutableIntStateOf(0)
        }
        val sailDirection = remember {
            mutableStateOf("North")
        }
        val healthPoints = remember {
            mutableIntStateOf(100)
        }
        val informationText = remember {
            mutableStateOf("")
        }
        val gameState = remember {
            mutableStateOf("")
        }
        // State Controls
        val buttonEnabled: (MutableState<String>, String, MutableIntState) -> Boolean = {direction, givenDirection, hp ->
            if (hp.intValue > 0) {
                direction.value != givenDirection
            }
            else {
                false
            }
        }
        val gameStateControl: (MutableState<String>, MutableIntState) -> Unit = { gState, hp ->
            if(hp.intValue <= 0) {
                gState.value = "Game Over!"
            }
        }
        val treasureOrStorm: (MutableIntState, MutableState<String>, MutableIntState) -> Unit = {
            treasureVal, info, hp ->
            if(Random.nextBoolean()) {
                info.value = "You found a treasure"
                treasureVal.intValue++
            }
            else {
                info.value = "You got caught in a storm"
                hp.intValue -= 5
            }
        }
        // Composable Elements
        Text(
            text = "HP: ${healthPoints.intValue}",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Treasure found: ${treasuresFound.intValue}",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Direction: ${sailDirection.value}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(
                onClick = {
                    sailDirection.value = "East"
                    treasureOrStorm(treasuresFound, informationText, healthPoints)
                    gameStateControl(gameState, healthPoints)
                },
                enabled = buttonEnabled(sailDirection, "East", healthPoints)
            ) {
                Text(
                    text = "Sail East",
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    sailDirection.value = "West"
                    treasureOrStorm(treasuresFound, informationText, healthPoints)
                    gameStateControl(gameState, healthPoints)

                },
                enabled = buttonEnabled(sailDirection, "West", healthPoints)
            ) {
                Text(
                    text = "Sail West",
                    maxLines = 2
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(
                onClick = {
                    sailDirection.value = "North"
                    treasureOrStorm(treasuresFound, informationText, healthPoints)
                    gameStateControl(gameState, healthPoints)

                },
                enabled = buttonEnabled(sailDirection, "North", healthPoints)
            ) {
                Text(
                    text = "Sail North",
                    maxLines = 2
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    sailDirection.value = "South"
                    treasureOrStorm(treasuresFound, informationText, healthPoints)
                    gameStateControl(gameState, healthPoints)

                },
                enabled = buttonEnabled(sailDirection, "South", healthPoints)
            ) {
                Text(
                    text = "Sail South",
                    maxLines = 2
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = informationText.value)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = gameState.value,
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(gameState.value == "Game Over!") {
            Button(
                onClick = {
                healthPoints.intValue = 100
                treasuresFound.intValue = 0
                sailDirection.value = "North"
                gameState.value = ""
                informationText.value = ""
            }
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Replay")
                Text(text = "Replay")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    CaptainGameTheme {
        CaptainGame()
    }
}
