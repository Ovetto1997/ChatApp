package luca.carlino.chatapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.db.entities.MessageEntity

@Dao
interface MessageDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMessages(messages: List<MessageEntity>)

    @Query("""
        SELECT * FROM messages 
        WHERE chat_id = :chatId 
        ORDER BY timestamp ASC
    """)
    fun getMessagesByChatId(chatId: Int): Flow<List<MessageEntity>>

    @Query("""
        SELECT * FROM messages 
        WHERE chat_id = :chatId 
        AND is_read = 0
    """)
    suspend fun getUnreadMessages(chatId: Int): List<MessageEntity>

    @Query("""
        SELECT * FROM messages 
        WHERE id = :messageId
    """)
    suspend fun getMessageById(messageId: Int): MessageEntity?

    @Update
    suspend fun updateMessage(message: MessageEntity)

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Query(
        """
        UPDATE messages 
        SET is_read = 1
        WHERE chat_id = :chatId
    """
    )
    suspend fun markMessagesAsRead(chatId: Int)
}

