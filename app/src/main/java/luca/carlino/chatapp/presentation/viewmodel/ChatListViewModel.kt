package luca.carlino.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import luca.carlino.chatapp.presentation.uistate.ChatListUiState
import luca.carlino.chatapp.domain.usecases.ObserveChatsUseCase
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val observeChats: ObserveChatsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ChatListUiState> =
        searchQuery
            .debounce(250)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                observeChats(query)
                    .map { chats ->
                        when {
                            chats.isEmpty() -> ChatListUiState.Empty("No chats found")
                            else -> ChatListUiState.Success(chats)
                        }
                    }
                    .onStart { emit(ChatListUiState.Loading) }
            }
            .catch { e -> emit(ChatListUiState.Error(e.message ?: "error")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ChatListUiState.Loading
            )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
