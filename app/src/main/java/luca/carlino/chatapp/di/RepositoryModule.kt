package luca.carlino.chatapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luca.carlino.chatapp.data.datasource.ChatLocalDataSource
import luca.carlino.chatapp.data.datasource.MessageLocalDataSource
import luca.carlino.chatapp.data.dto.ChatMapper
import luca.carlino.chatapp.data.dto.MessageMapper
import luca.carlino.chatapp.data.repository.ChatRepositoryImpl
import luca.carlino.chatapp.data.repository.MessageRepositoryImpl
import luca.carlino.chatapp.domain.repositories.ChatRepository
import luca.carlino.chatapp.domain.repositories.MessageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideChatRepository(
        localDataSource: ChatLocalDataSource
    ): ChatRepository = ChatRepositoryImpl(
        localDataSource = localDataSource,
        chatMapper = ChatMapper
    )

    @Provides
    @Singleton
    fun provideMessageLocalDataSource(
        localDataSource: MessageLocalDataSource
    ): MessageRepository = MessageRepositoryImpl(
        localDataSource = localDataSource,
        messageMapper = MessageMapper
    )
}