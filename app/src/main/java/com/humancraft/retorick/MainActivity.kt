package com.humancraft.retorick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import com.humancraft.retorick.ui.screen.CharacterScreen
import com.humancraft.retorick.ui.screen.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.collectAsState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val uiState = viewModel.uiState.collectAsState().value
                val currentPage = viewModel.currentPage.collectAsState().value
                CharacterScreen(
                    uiState = uiState,
                    onRefresh = {
                        viewModel.fetchCharacters(currentPage + 1)
                    },
                    onManualRefresh = {
                        viewModel.fetchCharacters(currentPage + 1)
                    }
                )
            }
        }
    }
}