package com.example.appplantas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class VideosUiState(
    val videoId1: String = "k3Wx5mefRGc",
    val videoId2: String = "YsJdGuTlU3I",
    val videoId3: String = "pL9K173yvoc",
    val videoId4: String = "ihGretS4hIA",
    val isLoading: Boolean = false,
    val error: String? = null
)

class VideosViewModel : ViewModel() {

    private val _uiState = MutableLiveData(VideosUiState())
    val uiState: LiveData<VideosUiState> = _uiState

    fun getVideoId(index: Int): String {
        return when (index) {
            1 -> _uiState.value?.videoId1 ?: "k3Wx5mefRGc"
            2 -> _uiState.value?.videoId2 ?: "YsJdGuTlU3I"
            3 -> _uiState.value?.videoId3 ?: "pL9K173yvoc"
            4 -> _uiState.value?.videoId4 ?: "ihGretS4hIA"
            else -> "k3Wx5mefRGc"
        }
    }
}
