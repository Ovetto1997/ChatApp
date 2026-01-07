package luca.carlino.chatapp.data.datasource

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.db.dao.ChatDao
import luca.carlino.chatapp.data.db.entities.ChatEntity

class ChatLocalDataSource(private val chatDao: ChatDao) {

    fun getAllChats(): Flow<List<ChatEntity>> = chatDao.getAllChats()


    fun searchChats(query: String): Flow<List<ChatEntity>> =
        if (query.isEmpty()) {
            getAllChats()
        } else {
            chatDao.searchChats(query)
        }

    suspend fun getChatById(chatId: Int): ChatEntity? =
        chatDao.getChatById(chatId)

    fun observeChat(chatId: Int): Flow<ChatEntity?> =
        chatDao.observeChat(chatId)

    suspend fun insertChat(chat: ChatEntity) =
        chatDao.insertChat(chat)

    suspend fun updateChat(chat: ChatEntity) =
        chatDao.updateChat(chat)

    suspend fun updateLastMessage(
        chatId: Int,
        lastMessage: String,
        timeStamp: Long
    ) = chatDao.updateChatLastMessage(chatId, lastMessage, timeStamp)

    suspend fun deleteChat(chat: ChatEntity) =
        chatDao.deleteChat(chat)

}