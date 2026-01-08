package luca.carlino.chatapp.data.datasource

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.datasource.abstraction.ChatLocalDataSource
import luca.carlino.chatapp.data.db.dao.ChatDao
import luca.carlino.chatapp.data.db.entities.ChatEntity
import javax.inject.Inject

class ChatLocalDataSourceImpl @Inject constructor(
    private val chatDao: ChatDao
) : ChatLocalDataSource {


    override fun getAllChats(): Flow<List<ChatEntity>> = chatDao.getAllChats()


    override fun searchChats(query: String): Flow<List<ChatEntity>> =
        chatDao.searchChats(query)

    override suspend fun getChatById(chatId: Long): ChatEntity? =
        chatDao.getChatById(chatId)

    override fun observeChat(chatId: Long): Flow<ChatEntity?> =
        chatDao.observeChat(chatId)

    override suspend fun insertChat(chat: ChatEntity) =
        chatDao.insertChat(chat)

    override suspend fun updateChat(chat: ChatEntity) =
        chatDao.updateChat(chat)

    override suspend fun updateLastMessage(
        chatId: Long,
        lastMessage: String,
        timeStamp: Long
    ) = chatDao.updateChatLastMessage(chatId, lastMessage, timeStamp)

    override suspend fun deleteChat(chat: ChatEntity) =
        chatDao.deleteChat(chat)

}