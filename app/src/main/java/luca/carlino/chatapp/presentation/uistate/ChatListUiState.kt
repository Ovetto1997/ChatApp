package luca.carlino.chatapp.presentation.uistate

sealed class ChatListUiState {
    object Loading : ChatListUiState()
    object Success : ChatListUiState()
    data class Empty(val message: String) : ChatListUiState()

    data class Error(val message: String) : ChatListUiState()
}