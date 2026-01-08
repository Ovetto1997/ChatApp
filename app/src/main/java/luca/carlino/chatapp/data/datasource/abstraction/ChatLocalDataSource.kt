package luca.carlino.chatapp.data.datasource.abstraction

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.db.entities.ChatEntity

interface ChatLocalDataSource {

    fun getAllChats(): Flow<List<ChatEntity>>


    fun searchChats(query: String): Flow<List<ChatEntity>>

    suspend fun getChatById(chatId: Long): ChatEntity?
    fun observeChat(chatId: Long): Flow<ChatEntity?>
    suspend fun insertChat(chat: ChatEntity)

    suspend fun updateChat(chat: ChatEntity)

    suspend fun updateLastMessage(
        chatId: Long,
        lastMessage: String,
        timeStamp: Long
    )

    suspend fun deleteChat(chat: ChatEntity)

}