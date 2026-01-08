package luca.carlino.chatapp.presentation.uistate

import luca.carlino.chatapp.domain.entities.Chat


//inserire la lista di chat di avere uno state flow a parte di chatListUiState.success
sealed interface ChatListUiState {
    data object Loading : ChatListUiState
    data class Success(val chats: List<Chat>) : ChatListUiState
    data class Empty(val message: String) : ChatListUiState

    data class Error(val message: String) : ChatListUiState
}