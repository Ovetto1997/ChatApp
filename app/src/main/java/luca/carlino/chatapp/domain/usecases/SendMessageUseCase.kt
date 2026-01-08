package luca.carlino.chatapp.domain.usecases

import luca.carlino.chatapp.domain.entities.Message
import luca.carlino.chatapp.domain.repository.ChatRepository
import luca.carlino.chatapp.domain.repository.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(message: Message) {
        messageRepository.insertMessage(message)
        chatRepository.updateChatLastMessage(
            chatId = message.chatId,
            lastMessage = message.text,
            timestamp = message.timestamp
        )
    }
}