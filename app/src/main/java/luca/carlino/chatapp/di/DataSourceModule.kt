package luca.carlino.chatapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luca.carlino.chatapp.data.datasource.ChatLocalDataSourceImpl
import luca.carlino.chatapp.data.datasource.MessageLocalDataSourceImpl
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
    ): ChatLocalDataSourceImpl = ChatLocalDataSourceImpl(chatDao)


    @Provides
    @Singleton
    fun provideMessageLocalDataSource(
        messageDao: MessageDao
    ): MessageLocalDataSourceImpl = MessageLocalDataSourceImpl(messageDao)
}