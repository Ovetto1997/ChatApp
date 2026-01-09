package luca.carlino.chatapp.domain.usecases


import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.core.Resource

import luca.carlino.chatapp.domain.entities.Chat
import luca.carlino.chatapp.data.repository.abstraction.ChatRepository
import javax.inject.Inject

class ObserveChatsUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Chat>>> {
        return if (query.isBlank()) {
            repository.getAllChats()
        } else {
            repository.searchChats(query)
        }
    }
}