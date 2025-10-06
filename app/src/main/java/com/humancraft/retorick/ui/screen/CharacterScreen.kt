package com.humancraft.retorick.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.humancraft.retorick.data.model.Character
import kotlinx.coroutines.delay

@Composable
fun CharacterScreen(
    uiState: UiState,
    onRefresh: () -> Unit,
    onManualRefresh: () -> Unit
) {
    var showError by remember { mutableStateOf<String?>(null) }

    // Auto refresh every 10 seconds after first load
    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            while (true) {
                delay(10000)
                onRefresh()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is UiState.InitialLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is UiState.Loading -> {
                // Show previous data with a loading indicator
                (uiState as? UiState.Success)?.let {
                    CharacterList(it.page, it.characters)
                }
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter))
            }
            is UiState.Success -> {
                CharacterList(uiState.page, uiState.characters)
            }
            is UiState.Error -> {
                showError = uiState.message
                // Show last valid data if available
            }
        }
        // Manual refresh button
        Button(
            onClick = onManualRefresh,
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ) {
            Text("Refrescar")
        }
        // Error message (Toast/Banner style)
        showError?.let { msg ->
            Snackbar(
                modifier = Modifier.align(Alignment.TopCenter),
                action = {
                    TextButton(onClick = { showError = null }) { Text("Cerrar") }
                }
            ) { Text(msg) }
        }
    }
}

@Composable
fun CharacterList(page: Int, characters: List<Character>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Página: $page",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(characters) { character ->
                CharacterItem(character)
                Divider()
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = character.name, fontWeight = FontWeight.Bold)
            Text(text = character.status)
        }
    }
}
