package luca.carlino.chatapp

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import luca.carlino.chatapp.data.datasource.abstraction.MessageLocalDataSource
import luca.carlino.chatapp.data.db.entities.MessageEntity
import luca.carlino.chatapp.data.dto.MessageMapper
import luca.carlino.chatapp.data.repository.MessageRepositoryImpl
import luca.carlino.chatapp.domain.entities.Message
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MessageRepositoryImplTest {

    private lateinit var repository: MessageRepositoryImpl

    @Mock
    private lateinit var localDataSource: MessageLocalDataSource

    @Mock
    private lateinit var messageMapper: MessageMapper


    private val testMessageEntity= MessageEntity(
        id = 1L,
        chatId = 1L,
        senderName = "Test",
        text = "Test message",
        timestamp = 1000L,
        isFromCurrentUser = true,
        isRead = false
    )
    
    private val testMessage = Message(
        chatId = 1L,
        senderName = "Test",
        text = "Test message",
        timestamp = 1000L,
        isFromCurrentUser = true,
        isRead = false
    )

    @Before
    fun setUp() {
        repository = MessageRepositoryImpl(localDataSource, messageMapper)
    }

    @Test
    fun getMessageByChatId_returns_mapped_messages() = runTest {

        val id = 1L
        val entities = listOf(testMessageEntity)
        val domainMessages = listOf(testMessage)


        whenever(localDataSource.getMessagesByChatId(chatId = id)).thenReturn(flowOf(entities))
        whenever(messageMapper.toDomainList(entities)).thenReturn(domainMessages)


        repository.getMessagesByChatId(chatId = id).test {
            assertEquals(domainMessages, awaitItem())
            awaitComplete()
        }

        @Suppress("UnusedFlow")
        verify(localDataSource).getMessagesByChatId(chatId = id)
        verify(messageMapper).toDomainList(entities)
    }

    @Test
    fun getMessageById_return_empty_list_when_no_messages_exist() = runTest{
        val id = 1L
        whenever(localDataSource.getMessagesByChatId(id)).thenReturn(flowOf(emptyList()))
        whenever(messageMapper.toDomainList(emptyList())).thenReturn(emptyList())

        repository.getMessagesByChatId(id).test { assertEquals(emptyList<Message>(), awaitItem())
        awaitComplete()
        }
    }

    @Test
    fun getUnreadMessages_returns_unread_messages_for_chat() = runTest {

        val id = 1L
        val unreadEntity = testMessageEntity.copy(isRead = false)
        val unreadMessage = testMessage.copy(isRead = false)
        val entities = listOf(unreadEntity)
        val domainMessages = listOf(unreadMessage)

        whenever(localDataSource.getUnreadMessages(id)).thenReturn(entities)
        whenever(messageMapper.toDomainList(entities)).thenReturn(domainMessages)

        val result = repository.getUnreadMessages(id)
        assertEquals(domainMessages, result)
        verify(localDataSource).getUnreadMessages(id)

    }

    @Test
    fun getUnreadMessages_returns_empty_list_when_all_messages_are_read()= runTest {
        val id = 1L
        whenever(localDataSource.getUnreadMessages(id)).thenReturn(emptyList())
        whenever(messageMapper.toDomainList(emptyList())).thenReturn(emptyList())


        val result = repository.getUnreadMessages(id)

        assertEquals(emptyList<Message>(),result)
    }

    @Test
    fun insertMessage_calls_data_source_with_mapped_entity()= runTest {
        whenever(messageMapper.toEntity(testMessage)).thenReturn(testMessageEntity)

        repository.insertMessage(testMessage)

        verify(localDataSource).insertMessage(testMessageEntity)
        verify(messageMapper).toEntity(testMessage)
    }

    @Test
     fun insertMessages_calls_data_source_with_mapped_entities() = runTest {
        // Given
        val message2 = testMessage. copy(chatId = 2L)
        val entity2 = testMessageEntity.copy(id = 2L)
        val messages = listOf(testMessage, message2)
        val entities = listOf(testMessageEntity, entity2)

        whenever(messageMapper.toEntity(testMessage)).thenReturn(testMessageEntity)
        whenever(messageMapper.toEntity(message2)).thenReturn(entity2)

        repository.insertMessages(messages)

        verify(localDataSource).insertAllMessages(entities)
    }


    @Test
    fun updateMessage_calls_data_source_with_mapped_entity()= runTest {
        val updatedMessage =testMessage.copy(text = "Updated text")
        val updatedEntity = testMessageEntity.copy(text = "Updated text")

        whenever(messageMapper.toEntity(updatedMessage)).thenReturn(updatedEntity)

        repository.updateMessage(updatedMessage)

        verify(localDataSource).updateMessage(updatedEntity)
    }

}