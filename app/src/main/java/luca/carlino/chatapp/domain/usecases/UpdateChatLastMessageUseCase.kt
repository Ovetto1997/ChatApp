package luca.carlino.chatapp.domain.usecases

import luca.carlino.chatapp.data.repository.abstraction.ChatRepository
import javax.inject.Inject

class UpdateChatLastMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        chatId: Long,
        lastMessage: String,
        timestamp: Long
    ) {
        chatRepository.updateChatLastMessage(
            chatId = chatId,
            lastMessage = lastMessage,
            timestamp = timestamp
        )
    }
}