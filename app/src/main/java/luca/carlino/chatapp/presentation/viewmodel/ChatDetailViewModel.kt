package luca.carlino.chatapp.presentation.viewmodel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import luca.carlino.chatapp.domain.entities.Chat
import luca.carlino.chatapp.domain.entities.Message
import luca.carlino.chatapp.domain.usecases.GetChatByIdUseCase
import luca.carlino.chatapp.domain.usecases.MarkMessagesAsReadUseCase
import luca.carlino.chatapp.domain.usecases.ObserveMessageByChatIdUseCase
import luca.carlino.chatapp.domain.usecases.SendMessageUseCase
import luca.carlino.chatapp.presentation.uistate.ChatDetailUiState
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val getChatById: GetChatByIdUseCase,
    private val observeMessagesByChatId: ObserveMessageByChatIdUseCase,
    private val markMessagesAsRead: MarkMessagesAsReadUseCase,
    private val sendMessageUseCase: SendMessageUseCase,

    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatId: Long = savedStateHandle.get<Long>("chatId") ?: -1L

    private val _chat = MutableStateFlow<Chat?>(null)
    val chat: StateFlow<Chat?> = _chat.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _newMessageText = MutableStateFlow("")
    val newMessageText: StateFlow<String> = _newMessageText.asStateFlow()

    private val _uiState = MutableStateFlow<ChatDetailUiState>(ChatDetailUiState.Loading)
    val uiState: StateFlow<ChatDetailUiState> = _uiState.asStateFlow()

    init {
        loadChat()
        loadMessages()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            observeMessagesByChatId(chatId).collect {
                _messages.value = it
            }
        }
    }

    private fun loadChat() {
        viewModelScope.launch {
            try {
                _uiState.value = ChatDetailUiState.Loading
                val chat = getChatById(chatId)
                _chat.value = chat
                if (chat != null) {
                    _uiState.value = ChatDetailUiState.Success
                    markMessagesAsRead(chatId)
                } else {
                    _uiState.value = ChatDetailUiState.Error("Chat not Found")
                }
            } catch (e: Exception) {
                _uiState.value = ChatDetailUiState.Error(e.message ?: "Unknow error")
            }
        }
    }
    
    fun sendMessage() {
        val text = _newMessageText.value
        if (text.isBlank() || chatId == -1L) return

        viewModelScope.launch {
            try {
                val message = Message(
                    chatId = chatId,
                    senderName = "You",
                    text = _newMessageText.value,
                    timestamp = System.currentTimeMillis(),
                    isFromCurrentUser = true,
                    isRead = true,

                )

                sendMessageUseCase(message)

                _newMessageText.value = ""
            } catch (e : Exception) {
                _uiState.value = ChatDetailUiState.Error(e.message ?: "Failed to send message")
            }
        }
    }

    fun updateMessageText(text: String) {
        _newMessageText.value = text
    }

}
