package com.humancraft.retorick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.humancraft.retorick.ui.theme.RetoRickTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetoRickTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val uiState = viewModel.uiState.collectAsState().value
                    CharacterScreen(
                        uiState = uiState,
                        onRefresh = { viewModel.fetchCharacters(viewModel.getLastValid().first) },
                        onManualRefresh = { viewModel.fetchCharacters(viewModel.getLastValid().first) }
                    )
                }
            }
        }
        // Primera carga
        viewModel.fetchCharacters(1)
    }
    private val viewModel: CharacterViewModel by viewModels()
}
import androidx.activity.viewModels
import com.humancraft.retorick.ui.screen.CharacterScreen
import com.humancraft.retorick.ui.screen.CharacterViewModel
import com.humancraft.retorick.ui.screen.UiState
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetoRickTheme {
        Greeting("Android")
    }
}