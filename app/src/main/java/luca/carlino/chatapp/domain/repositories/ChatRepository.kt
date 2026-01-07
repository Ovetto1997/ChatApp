package luca.carlino.chatapp.domain.repositories


import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.domain.entities.Chat


interface ChatRepository {

    fun getAllChats(): Flow<List<Chat>>

    fun searchChats(query: String): Flow<List<Chat>>

    suspend fun getChatById(chatId: Int): Chat?

    fun observeChat(chatId: Int): Flow<Chat?>

    suspend fun insertChat(chat: Chat)

    suspend fun updateChat(chat: Chat)

    suspend fun updateChatLastMessage(
        chatId: Int,
        lastMessage: String,
        timestamp: Long
    )

    suspend fun deleteChat(chat: Chat)
}