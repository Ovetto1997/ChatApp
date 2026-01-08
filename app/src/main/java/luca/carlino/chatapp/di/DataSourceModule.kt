package luca.carlino.chatapp.di

import dagger.Binds
import dagger.Module

import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luca.carlino.chatapp.data.datasource.ChatLocalDataSourceImpl

import luca.carlino.chatapp.data.datasource.MessageLocalDataSourceImpl
import luca.carlino.chatapp.data.datasource.abstraction.ChatLocalDataSource
import luca.carlino.chatapp.data.datasource.abstraction.MessageLocalDataSource

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideChatLocalDataSource(
        impl: ChatLocalDataSourceImpl
    ): ChatLocalDataSource


    @Binds
    @Singleton
    abstract fun provideMessageLocalDataSource(
       impl: MessageLocalDataSourceImpl
    ): MessageLocalDataSource
}