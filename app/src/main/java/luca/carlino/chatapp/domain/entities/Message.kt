package luca.carlino.chatapp.domain.entities

import android.icu.util.Calendar

import java.text.SimpleDateFormat
import java.util.Date

data class Message(
    val chatId: Long,
    val senderName: String,
    val text: String,
    val timestamp: Long,
    val isFromCurrentUser: Boolean,
    val isRead: Boolean = false
) {
    fun getFormattedTime(): String {


        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp

        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)

        return when {
            calendar.timeInMillis >= today.timeInMillis -> {
                SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(Date(timestamp))
            }

            else -> {
                SimpleDateFormat("MMM dd HH:mm", java.util.Locale.getDefault()).format(Date(
                    timestamp
                ))

            }
        }
    }
}


