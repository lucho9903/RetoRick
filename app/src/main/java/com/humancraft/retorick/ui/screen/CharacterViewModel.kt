package com.humancraft.retorick.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.humancraft.retorick.data.model.Character
import com.humancraft.retorick.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object InitialLoading : UiState()
    data class Success(val page: Int, val characters: List<Character>) : UiState()
    data class Error(val message: String) : UiState()
    object Loading : UiState()
}

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.InitialLoading)
    val uiState: StateFlow<UiState> = _uiState

    private var lastValidCharacters: List<Character> = emptyList()
    private var firstLoadDone = false

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    init {
        fetchCharacters(1)
    }

    fun fetchCharacters(page: Int = _currentPage.value) {
        viewModelScope.launch {
            if (!firstLoadDone) {
                _uiState.value = UiState.InitialLoading
            } else {
                _uiState.value = UiState.Loading
            }
            try {
                val response = repository.getCharacters(page)
                Log.d("CharacterViewModel", "HTTP response: code=${response.code()}, isSuccessful=${response.isSuccessful}")
                if (!response.isSuccessful) {
                    Log.e("CharacterViewModel", "Error response body: ${response.errorBody()?.string()}")
                }
                val body = response.body()
                Log.d("CharacterViewModel", "Response body: $body")
                val characters = body?.results?.take(3) ?: emptyList()
                if (characters.isNotEmpty()) {
                    _currentPage.value = page
                    lastValidCharacters = characters
                    _uiState.value = UiState.Success(page, characters)
                    firstLoadDone = true
                } else {
                    _uiState.value = UiState.Error("Respuesta vacía del servidor")
                }
            } catch (e: Exception) {
                Log.e("CharacterViewModel", "Excepción al obtener personajes", e)
                _uiState.value = UiState.Error("Error de red o decodificación: ${e.localizedMessage}")
            }
        }
    }

    fun nextPage() {
        fetchCharacters(_currentPage.value + 1)
    }

    fun prevPage() {
        if (_currentPage.value > 1) {
            fetchCharacters(_currentPage.value - 1)
        }
    }

    fun retryLast() {
        fetchCharacters(_currentPage.value)
    }

    fun getLastValid(): Pair<Int, List<Character>> = _currentPage.value to lastValidCharacters
}
