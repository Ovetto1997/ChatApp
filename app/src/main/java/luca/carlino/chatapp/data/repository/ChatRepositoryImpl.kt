package luca.carlino.chatapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import luca.carlino.chatapp.data.datasource.abstraction.ChatLocalDataSource
import luca.carlino.chatapp.data.dto.ChatMapper
import luca.carlino.chatapp.domain.entities.Chat
import luca.carlino.chatapp.domain.repositories.ChatRepository


class ChatRepositoryImpl (
    private val localDataSource: ChatLocalDataSource,
    private val chatMapper: ChatMapper
) : ChatRepository {

    override fun getAllChats(): Flow<List<Chat>> =
        localDataSource.getAllChats().map { chatMapper.toDomainList(it) }


    override fun searchChats(query: String): Flow<List<Chat>> =
        localDataSource.searchChats(query).map { chatMapper.toDomainList(it) }


    override suspend fun getChatById(chatId: Int): Chat? =
        localDataSource.getChatById(chatId)?.let { chatMapper.toDomain(it) }


    override fun observeChat(chatId: Int): Flow<Chat?> =
        localDataSource.observeChat(chatId).map { entity -> entity?.let { chatMapper.toDomain(it) } }


    override suspend fun insertChat(chat: Chat) =
        localDataSource.insertChat(chatMapper.toEntity(chat))



    override suspend fun updateChat(chat: Chat) =
        localDataSource.updateChat(chatMapper.toEntity(chat))



    override suspend fun updateChatLastMessage(
        chatId: Int,
        lastMessage: String,
        timestamp: Long
    ) =
        localDataSource.updateLastMessage(chatId, lastMessage, timestamp)



    override suspend fun deleteChat(chat: Chat) =
        localDataSource.deleteChat(chatMapper.toEntity(chat))


}