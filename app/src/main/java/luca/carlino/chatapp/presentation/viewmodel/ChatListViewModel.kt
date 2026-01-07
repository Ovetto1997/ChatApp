package luca.carlino.chatapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import luca.carlino.chatapp.domain.entities.Chat
import luca.carlino.chatapp.domain.repositories.ChatRepository
import luca.carlino.chatapp.presentation.uistate.ChatListUiState
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getChatRepository: ChatRepository,
    ) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery : StateFlow<String> = _searchQuery.asStateFlow()
    private val _uiState = MutableStateFlow<ChatListUiState>(ChatListUiState.Loading)

    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val chats: StateFlow<List<Chat>> = searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                getChatRepository.getAllChats()
            } else {
                getChatRepository.searchChats(query)
            }
        }
        .onEach { chats ->
            _uiState.value = if (chats.isEmpty() && searchQuery.value.isNotEmpty()) {
                ChatListUiState.Empty("No chats found")
            } else if (chats.isEmpty()) {
                ChatListUiState.Empty("No chats available")
            } else {
                ChatListUiState.Success
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }
}






