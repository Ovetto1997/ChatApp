package luca.carlino.chatapp

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import luca.carlino.chatapp.core.Resource
import luca.carlino.chatapp.data.datasource.abstraction.ChatLocalDataSource
import luca.carlino.chatapp.data.db.entities.ChatEntity
import luca.carlino.chatapp.data.dto.ChatMapper
import luca.carlino.chatapp.data.repository.ChatRepositoryImpl
import luca.carlino.chatapp.data.repository.abstraction.ChatRepository
import luca.carlino.chatapp.domain.entities.Chat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@Suppress("UnusedFlow")
@RunWith(MockitoJUnitRunner::class)
class ChatRepositoryImplTest {

    private lateinit var repository: ChatRepository


    @Mock
    private lateinit var localDataSource: ChatLocalDataSource

    @Mock
    private lateinit var chatMapper: ChatMapper


    private val testChatEntity = ChatEntity(
        id = 1L,
        name = "Test chat",
        imageUrl = "https://example.com/avatar.png" ,
        lastMessage = "Hello this is a test",
        lastMessageTime = 1000L,
        isOnline = false,
        createdAt = 1000L
    )

    private val testChat = Chat(
        id = 1L,
        name = "Test chat",
        imageUrl = "https://example.com/avatar.png",
        lastMessage = "Hello this is a test",
        lastMessageTime = 1000L,
        isOnline = false,
        createdAt = 1000L
    )


    @Before
    fun setUp() {
        repository = ChatRepositoryImpl(localDataSource, chatMapper)
    }

    @Test
    fun getAllChats_returns_mapped_chat_as_Resource_Success()= runTest{
        val entities = listOf(testChatEntity)
        val domainChats = listOf(testChat)

        whenever(localDataSource.getAllChats()).thenReturn(flowOf(entities))
        whenever(chatMapper.toDomainList(entities)).thenReturn(domainChats)

        repository.getAllChats().test {
            val firstResult= awaitItem()
            if (firstResult is Resource.Loading) {
                val successResult = awaitItem()
                assert(successResult is Resource.Success)
                assertEquals(domainChats, (successResult as Resource.Success).data)
            }else {
                assert(firstResult is Resource.Success)
                assertEquals(domainChats, (firstResult as Resource.Success).data)
            }
            awaitComplete()
        }
        verify(localDataSource).getAllChats()
        verify(chatMapper).toDomainList(entities)
    }

    @Test
    fun getAllChats_returns_empty_list_when_no_chats_exist()= runTest {
        whenever(localDataSource.getAllChats()).thenReturn(flowOf(emptyList()))
        whenever(chatMapper.toDomainList(emptyList())).thenReturn(emptyList())


        repository.getAllChats().test {
            val firstResult = awaitItem()
            if (firstResult is Resource.Loading) {
                val successResult = awaitItem()
                assert(successResult is Resource.Success)
                assertEquals(emptyList<Chat>(), (successResult as Resource.Success).data)

            } else {
                assert(firstResult is Resource.Success)
                assertEquals(emptyList<Chat>(), (firstResult as Resource.Success).data)
            }
            awaitComplete()
        }


    }



}