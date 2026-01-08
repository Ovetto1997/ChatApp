package luca.carlino.chatapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import luca.carlino.chatapp.data.db.entities.ChatEntity

//DAO for handling operations related to a chat
// table/entity, save chats' hours and texters
@Dao
interface ChatDao {

    //avoid duplication and automatically update existing items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllChats(chats: List<ChatEntity>)
    // Read operations
    @Query("""
        SELECT * FROM chats 
        ORDER BY last_message_time DESC
    """)
    fun getAllChats(): Flow<List<ChatEntity>>

    @Query("""
        SELECT * FROM chats 
        WHERE chat_name LIKE '%' || :searchQuery || '%'
        ORDER BY last_message_time DESC
    """)
    fun searchChats(searchQuery: String): Flow<List<ChatEntity>>

    @Query("SELECT * FROM chats WHERE id = :chatId")
    suspend fun getChatById(chatId: Long): ChatEntity?

    @Query("SELECT * FROM chats WHERE id = :chatId")
    fun observeChat(chatId: Long): Flow<ChatEntity?>

    // Update operations
    @Update
    suspend fun updateChat(chat: ChatEntity)

    @Query("""
        UPDATE chats 
        SET last_message = :lastMessage, last_message_time = :timestamp
        WHERE id = :chatId
    """)
    suspend fun updateChatLastMessage(
        chatId: Long,
        lastMessage: String,
        timestamp: Long
    )

    // Delete operations
    @Delete
    suspend fun deleteChat(chat: ChatEntity)

    @Query("DELETE FROM chats WHERE id = :chatId")
    suspend fun deleteChatById(chatId: Int)

}