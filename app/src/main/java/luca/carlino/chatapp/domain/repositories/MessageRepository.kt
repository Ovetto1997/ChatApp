package luca.carlino.chatapp.domain.repositories

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.domain.entities.Message


interface MessageRepository {
    fun getMessagesByChatId(chatId: Int): Flow<List<Message>>

    suspend fun getUnreadMessages(chatId: Int): List<Message>

    suspend fun insertMessage(message: Message)

    suspend fun insertMessages(messages: List<Message>)

    suspend fun updateMessage(message: Message)

    suspend fun deleteMessage(message: Message)

    suspend fun markMessagesAsRead(chatId: Int)
}