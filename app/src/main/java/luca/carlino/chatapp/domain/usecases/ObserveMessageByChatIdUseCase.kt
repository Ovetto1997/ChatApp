package luca.carlino.chatapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.domain.entities.Message
import luca.carlino.chatapp.domain.repository.MessageRepository
import javax.inject.Inject

class ObserveMessageByChatIdUseCase @Inject constructor(
    private val repository: MessageRepository
){
    operator fun invoke(chatId: Long): Flow<List<Message>> =
    repository.getMessagesByChatId(chatId)
}