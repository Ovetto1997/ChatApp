package luca.carlino.chatapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luca.carlino.chatapp.data.db.dao.ChatDao
import luca.carlino.chatapp.data.db.dao.MessageDao
import luca.carlino.chatapp.data.db.entities.ChatEntity
import luca.carlino.chatapp.data.db.entities.MessageEntity

@Database(
    entities = [ChatEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao() : ChatDao
    abstract fun messageDao(): MessageDao

    companion object {

        const val DATABASE_NAME = "chat_app"

        @Volatile //ensure visibility across threads
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            /***se instance non è nulla, return**/
            //if Instance nullo, esegui il codice interno
            // synchronized prevent race conditions when initializing the db, only one by one thread can enter in the blocK
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Pre-popola il database con dati iniziali
                            // Importante: lanciare su thread separato con Coroutine
                            CoroutineScope(Dispatchers.IO).launch {
                                val instance = INSTANCE!!
                                preFillDatabase(instance)
                            }
                        }

                    }).build()
                INSTANCE = instance
                instance

            }

        }

        private suspend fun preFillDatabase(database: AppDatabase) {
            val chatDao = database.chatDao()
            val messageDao = database.messageDao()
            val currentTime = System.currentTimeMillis()

            val preFilledChats = listOf(
                ChatEntity(
                    id = 1,
                    name = "Alice Johnson",
                    imageUrl = "https://randomuser.me/api/portraits/women/1.jpg",
                    lastMessage = "Hey! How are you?",
                    lastMessageTime = currentTime,
                    isOnline = true,
                    createdAt = currentTime - 86400000 // 1 day ago
                ),
                ChatEntity(
                    id = 2,
                    name = "Bob Smith",
                    imageUrl = "https://randomuser.me/api/portraits/men/2.jpg",
                    lastMessage = "See you later!",
                    lastMessageTime = currentTime - 3600000, // 1 hour ago
                    isOnline = false,
                    createdAt = currentTime - 172800000 // 2 days ago
                ),
                ChatEntity(
                    id = 3,
                    name = "Carol White",
                    imageUrl = "https://randomuser.me/api/portraits/women/3.jpg",
                    lastMessage = "Thanks for your help!",
                    lastMessageTime = currentTime - 7200000, // 2 hours ago
                    isOnline = true,
                    createdAt = currentTime - 259200000 // 3 days ago
                )
            )
            chatDao.insertAllChats(preFilledChats)


            val preFilledMessages = listOf(
                MessageEntity(
                    chatId = 1,
                    senderName = "Alice Johnson",
                    text = "Hey! How are you?",
                    timestamp = currentTime,
                    isFromCurrentUser = false,
                    isRead = true
                ),
                MessageEntity(
                    chatId = 1,
                    senderName = "You",
                    text = "Hi Alice! I'm doing great, thanks for asking!",
                    timestamp = currentTime - 60000,
                    isFromCurrentUser = true,
                    isRead = true
                ),
                MessageEntity(
                    chatId = 1,
                    senderName = "Alice Johnson",
                    text = "That's awesome! Want to grab coffee tomorrow?",
                    timestamp = currentTime - 120000,
                    isFromCurrentUser = false,
                    isRead = true
                ),

                // Chat 2 messages
                MessageEntity(
                    chatId = 2,
                    senderName = "Bob Smith",
                    text = "See you later!",
                    timestamp = currentTime - 3600000,
                    isFromCurrentUser = false,
                    isRead = true
                ),

                // Chat 3 messages
                MessageEntity(
                    chatId = 3,
                    senderName = "Carol White",
                    text = "Thanks for your help!",
                    timestamp = currentTime - 7200000,
                    isFromCurrentUser = false,
                    isRead = true
                ),
                MessageEntity(
                    chatId = 3,
                    senderName = "You",
                    text = "You're welcome! Happy to help.",
                    timestamp = currentTime - 7260000,
                    isFromCurrentUser = true,
                    isRead = true
                )
            )
            messageDao.insertAllMessages(preFilledMessages)
        }
    }
}
