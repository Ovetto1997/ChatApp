package luca.carlino.chatapp.data.dto

import luca.carlino.chatapp.data.db.entities.ChatEntity
import luca.carlino.chatapp.domain.entities.Chat

object ChatMapper {


    fun toDomain(entity: ChatEntity): Chat {
        return Chat(
            id = entity.id,
            name = entity.name,
            imageUrl = entity.imageUrl,
            lastMessage = entity.lastMessage,
            lastMessageTime = entity.lastMessageTime,
            isOnline = entity.isOnline,
            createdAt = entity.createdAt
        )
    }

    fun toEntity(domain: Chat): ChatEntity {
        return ChatEntity(
            id = domain.id,
            name = domain.name,
            imageUrl = domain.imageUrl,
            lastMessage = domain.lastMessage,
            lastMessageTime = domain.lastMessageTime,
            isOnline = domain.isOnline,
            createdAt = domain.createdAt
        )
    }
    fun toDomainList(entities: List<ChatEntity>): List<Chat> {
        return entities.map { toDomain(it) }
    }

}