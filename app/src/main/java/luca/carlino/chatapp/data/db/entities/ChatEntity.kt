package luca.carlino.chatapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "chats", indices = [Index("id")])
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "chat_name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "last_message")
    val lastMessage: String,

    @ColumnInfo(name = "last_message_time")
    val lastMessageTime: Long,

    @ColumnInfo(name = "is_online")
    val isOnline: Boolean = false,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()

)