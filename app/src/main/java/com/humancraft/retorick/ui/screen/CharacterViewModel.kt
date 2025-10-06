package com.humancraft.retorick.ui.screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var lastValidPage: Int = 1
    private var lastValidCharacters: List<Character> = emptyList()
    private var firstLoadDone = false

    fun fetchCharacters(page: Int) {
        viewModelScope.launch {
            if (!firstLoadDone) {
                _uiState.value = UiState.InitialLoading
            } else {
                _uiState.value = UiState.Loading
            }
            try {
                val response = repository.getCharacters(page)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val characters = body.results.take(3)
                        lastValidPage = page
                        lastValidCharacters = characters
                        _uiState.value = UiState.Success(page, characters)
                        firstLoadDone = true
                    } else {
                        _uiState.value = UiState.Error("Respuesta vacía del servidor")
                    }
                } else {
                    _uiState.value = UiState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error de red o decodificación")
            }
        }
    }

    fun retryLast() {
        fetchCharacters(lastValidPage)
    }

    fun getLastValid(): Pair<Int, List<Character>> = lastValidPage to lastValidCharacters
}
