package luca.carlino.chatapp.domain.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Contextual
import luca.carlino.chatapp.domain.entities.Chat


interface ChatRepository {

    fun getAllChats(): Flow<List<Chat>>

    fun searchChats(query: String): Flow<List<Chat>>

    suspend fun getChatById(chatId: Long): Chat?

    fun observeChat(chatId: Long): Flow<Chat?>

    suspend fun insertChat(chat: Chat)

    suspend fun updateChat(chat: Chat)

    suspend fun updateChatLastMessage(
        chatId: Long,
        lastMessage: String,
        timestamp: Long
    )

    suspend fun deleteChat(chat: Chat)
}