package luca.carlino.chatapp.domain.usecases

import luca.carlino.chatapp.domain.entities.Chat
import luca.carlino.chatapp.data.repository.abstraction.ChatRepository
import javax.inject.Inject

class GetChatByIdUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(chatId: Long): Chat? =
        repository.getChatById(chatId)
}