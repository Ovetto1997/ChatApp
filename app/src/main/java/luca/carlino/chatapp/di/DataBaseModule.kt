package luca.carlino.chatapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import luca.carlino.chatapp.data.db.AppDatabase
import luca.carlino.chatapp.data.db.dao.ChatDao
import luca.carlino.chatapp.data.db.dao.MessageDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideChatDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase = AppDatabase.getDatabase(context)

    @Provides
    fun provieChatDao(database: AppDatabase) : ChatDao =
        database.chatDao()

    @Provides
    fun provideMessageDao(database: AppDatabase): MessageDao =
        database.messageDao()
}