package luca.carlino.chatapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luca.carlino.chatapp.data.datasource.ChatLocalDataSource
import luca.carlino.chatapp.data.datasource.MessageLocalDataSource
import luca.carlino.chatapp.data.db.dao.ChatDao
import luca.carlino.chatapp.data.db.dao.MessageDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideChatLocalDataSource(
        chatDao: ChatDao
    ): ChatLocalDataSource = ChatLocalDataSource(chatDao)


    @Provides
    @Singleton
    fun provideMessageLocalDataSource(
        messageDao: MessageDao
    ): MessageLocalDataSource = MessageLocalDataSource(messageDao)
}