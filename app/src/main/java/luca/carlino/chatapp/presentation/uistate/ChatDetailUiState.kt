package luca.carlino.chatapp.presentation.uistate

sealed class ChatDetailUiState {
    object Loading : ChatDetailUiState()
    object Success : ChatDetailUiState()
    data class Error(val message: String) : ChatDetailUiState()
}
