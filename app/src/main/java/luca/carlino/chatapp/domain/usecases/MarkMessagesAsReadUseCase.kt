package luca.carlino.chatapp.domain.usecases

import luca.carlino.chatapp.data.repository.abstraction.MessageRepository
import javax.inject.Inject

class MarkMessagesAsReadUseCase @Inject constructor(
    private val repository: MessageRepository
){
    suspend operator fun invoke(chatId: Long) =
        repository.markMessagesAsRead(chatId)
}