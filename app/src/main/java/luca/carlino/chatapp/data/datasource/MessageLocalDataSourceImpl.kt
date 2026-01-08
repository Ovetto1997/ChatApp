package luca.carlino.chatapp.data.datasource

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.datasource.abstraction.MessageLocalDataSource
import luca.carlino.chatapp.data.db.dao.MessageDao
import luca.carlino.chatapp.data.db.entities.MessageEntity
import javax.inject.Inject


class MessageLocalDataSourceImpl @Inject constructor(
    private val messageDao: MessageDao
) : MessageLocalDataSource{


    override fun getMessagesByChatId(chatId: Long): Flow<List<MessageEntity>> =
        messageDao.getMessagesByChatId(chatId)

    override suspend fun getUnreadMessages(chatId: Long): List<MessageEntity> =
        messageDao.getUnreadMessages(chatId)

    override suspend fun insertMessage(message: MessageEntity) =
        messageDao.insertMessage(message)

    override suspend fun insertAllMessages(messages: List<MessageEntity>) =
        messageDao.insertAllMessages(messages)

    override suspend fun updateMessage(message: MessageEntity) =
        messageDao.updateMessage(message)

    override suspend fun deleteMessage(message: MessageEntity) =
        messageDao.deleteMessage(message)

    override suspend fun markMessagesAsRead(chatId: Long) =
        messageDao.markMessagesAsRead(chatId)


    }