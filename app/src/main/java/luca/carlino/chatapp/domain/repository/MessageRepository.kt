package luca.carlino.chatapp.domain.repository

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.domain.entities.Message


interface MessageRepository {
    fun getMessagesByChatId(chatId: Long): Flow<List<Message>>

    suspend fun getUnreadMessages(chatId: Long): List<Message>

    suspend fun insertMessage(message: Message)

    suspend fun insertMessages(messages: List<Message>)

    suspend fun updateMessage(message: Message)

    suspend fun deleteMessage(message: Message)

    suspend fun markMessagesAsRead(chatId: Long)
}