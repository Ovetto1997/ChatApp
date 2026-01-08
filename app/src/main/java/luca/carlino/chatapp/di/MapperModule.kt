package luca.carlino.chatapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luca.carlino.chatapp.data.dto.ChatMapper

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Provides fun provideChatMapper(): ChatMapper = ChatMapper
    @Provides fun provideMessageMapper(): ChatMapper = ChatMapper
}