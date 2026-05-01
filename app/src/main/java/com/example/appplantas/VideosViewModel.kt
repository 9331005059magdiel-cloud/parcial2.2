package com.example.appplantas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class VideosUiState(
    val videoId1: String = "k3Wx5mefRGc",
    val videoId2: String = "YsJdGuTlU3I",
    val videoId3: String = "pL9K173yvoc",
    val videoId4: String = "ihGretS4hIA"
) {
    fun asList(): List<String> = listOf(videoId1, videoId2, videoId3, videoId4)
        .map { id -> 
            // Filtro extremo: solo caracteres válidos de YouTube y exactamente 11 de largo
            id.trim().filter { it.isLetterOrDigit() || it == '-' || it == '_' }.take(11)
        }
}

class VideosViewModel : ViewModel() {
    private val _uiState = MutableLiveData(VideosUiState())
    val uiState: LiveData<VideosUiState> = _uiState
}
