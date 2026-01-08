package luca.carlino.chatapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import luca.carlino.chatapp.data.datasource.abstraction.MessageLocalDataSource
import luca.carlino.chatapp.data.dto.MessageMapper
import luca.carlino.chatapp.domain.entities.Message
import luca.carlino.chatapp.domain.repositories.MessageRepository


class MessageRepositoryImpl (
    private val localDataSource: MessageLocalDataSource,
    private val messageMapper: MessageMapper
) : MessageRepository{
    override fun getMessagesByChatId(chatId: Int): Flow<List<Message>> =
        localDataSource.getMessagesByChatId(chatId).map { messageMapper.toDomainList(it) }



    override suspend fun getUnreadMessages(chatId: Int): List<Message> =
        localDataSource.getUnreadMessages(chatId).let { messageMapper.toDomainList(it) }



    override suspend fun insertMessage(message: Message) =
        localDataSource.insertMessage(messageMapper.toEntity(message))



    override suspend fun insertMessages(messages: List<Message>) =
        localDataSource.insertAllMessages(messages.map { messageMapper.toEntity(it) })


    override suspend fun updateMessage(message: Message) =
        localDataSource.updateMessage(messageMapper.toEntity(message))


    override suspend fun deleteMessage(message: Message) =
        localDataSource.deleteMessage(messageMapper.toEntity(message))


    override suspend fun markMessagesAsRead(chatId: Int) =
        localDataSource.markMessagesAsRead(chatId)

}