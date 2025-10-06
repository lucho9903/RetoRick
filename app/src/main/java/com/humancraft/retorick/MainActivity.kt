package com.humancraft.retorick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.humancraft.retorick.ui.screen.CharacterScreen
import com.humancraft.retorick.ui.screen.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val uiState = viewModel.uiState.collectAsState().value
                CharacterScreen(
                    uiState = uiState,
                    onRefresh = { viewModel.fetchCharacters(viewModel.getLastValid().first) },
                    onManualRefresh = { viewModel.fetchCharacters(viewModel.getLastValid().first) }
                )
            }
        }
        // Primera carga
        viewModel.fetchCharacters(1)
    }
}