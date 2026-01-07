package luca.carlino.chatapp.domain.entities

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class Chat(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val lastMessage: String,
    val lastMessageTime: Long,
    val isOnline: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun getFormattedLastMessageTime(): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = lastMessageTime

        val today = Calendar.getInstance()


        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)

        return when {
            calendar.timeInMillis >= today.timeInMillis -> {
                // Today - show time only
                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(lastMessageTime))
            }
            calendar.before(today) -> {
                // Previous days - show date
                SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(lastMessageTime))
            }
            else -> ""
        }
    }
}
