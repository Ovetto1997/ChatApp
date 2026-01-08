package luca.carlino.chatapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import luca.carlino.chatapp.data.repository.ChatRepositoryImpl
import luca.carlino.chatapp.data.repository.MessageRepositoryImpl
import luca.carlino.chatapp.domain.repository.ChatRepository
import luca.carlino.chatapp.domain.repository.MessageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        impl: ChatRepositoryImpl
    ) : ChatRepository



    @Binds
    @Singleton
   abstract fun bindMessageRepository(
       impl: MessageRepositoryImpl
    ): MessageRepository
}