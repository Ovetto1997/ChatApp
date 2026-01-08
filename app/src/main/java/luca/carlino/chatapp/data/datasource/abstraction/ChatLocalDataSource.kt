package luca.carlino.chatapp.data.datasource.abstraction

import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.db.entities.ChatEntity

interface ChatLocalDataSource {

    fun getAllChats(): Flow<List<ChatEntity>>


    fun searchChats(query: String): Flow<List<ChatEntity>>

    suspend fun getChatById(chatId: Int): ChatEntity?
    fun observeChat(chatId: Int): Flow<ChatEntity?>
    suspend fun insertChat(chat: ChatEntity)

    suspend fun updateChat(chat: ChatEntity)

    suspend fun updateLastMessage(
        chatId: Int,
        lastMessage: String,
        timeStamp: Long
    )

    suspend fun deleteChat(chat: ChatEntity)

}