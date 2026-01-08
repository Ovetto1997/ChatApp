package luca.carlino.chatapp.data.datasource.abstraction

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.db.entities.MessageEntity

interface MessageLocalDataSource {

    fun getMessagesByChatId(chatId: Long): Flow<List<MessageEntity>>

    suspend fun getUnreadMessages(chatId: Int): List<MessageEntity>

    suspend fun insertMessage(message: MessageEntity)

    suspend fun insertAllMessages(messages: List<MessageEntity>)

    suspend fun updateMessage(message: MessageEntity)


    suspend fun deleteMessage(message: MessageEntity)


    suspend fun markMessagesAsRead(chatId: Long)

}