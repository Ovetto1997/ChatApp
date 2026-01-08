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
import luca.carlino.chatapp.domain.repository.ChatRepository
import luca.carlino.chatapp.domain.repository.MessageRepository
import luca.carlino.chatapp.presentation.uistate.ChatDetailUiState
import javax.inject.Inject

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chatId: Long = savedStateHandle.get<Long>("chatId") ?: -1

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
            messageRepository.getMessagesByChatId(chatId)
                .collect { messageList ->
                    messageList.also { _messages.value = it }
                }
        }
    }

    private fun loadChat() {
        viewModelScope.launch {
            try {
                _uiState.value = ChatDetailUiState.Loading
                val chat = chatRepository.getChatById(chatId)
                _chat.value = chat

                if (chat != null) {
                    _uiState.value = ChatDetailUiState.Success
                    messageRepository.markMessagesAsRead(chatId)
                } else {

                    _uiState.value = ChatDetailUiState.Error("Chat not Found")

                }
            } catch (e: Exception) {
                _uiState.value = ChatDetailUiState.Error(e.message ?: "Unknow error")
            }
        }
    }
    fun sendMessage() {
        if (_newMessageText.value.isBlank() || chatId == (-1).toLong()) return
//
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

                messageRepository.insertMessage(message)

                // Update chat's last message
                chatRepository.updateChatLastMessage(
                    chatId = chatId,
                    lastMessage = message.text,
                    timestamp = message.timestamp
                )

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
