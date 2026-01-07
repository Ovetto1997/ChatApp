package luca.carlino.chatapp.data.datasource

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.db.dao.MessageDao
import luca.carlino.chatapp.data.db.entities.MessageEntity


class MessageLocalDataSource(private val messageDao: MessageDao) {

    fun getMessagesByChatId(chatId: Int): Flow<List<MessageEntity>> =
        messageDao.getMessagesByChatId(chatId)

    suspend fun getUnreadMessages(chatId: Int): List<MessageEntity> =
        messageDao.getUnreadMessages(chatId)

    suspend fun insertMessage(message: MessageEntity) =
        messageDao.insertMessage(message)

    suspend fun insertAllMessages(messages: List<MessageEntity>) =
        messageDao.insertAllMessages(messages)

    suspend fun updateMessage(message: MessageEntity) =
        messageDao.updateMessage(message)

    suspend fun deleteMessage(message: MessageEntity) =
        messageDao.deleteMessage(message)

    suspend fun markMessagesAsRead(chatId: Int) =
        messageDao.markMessagesAsRead(chatId)


}