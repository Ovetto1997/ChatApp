package luca.carlino.chatapp.data.dto

import luca.carlino.chatapp.data.db.entities.MessageEntity
import luca.carlino.chatapp.domain.entities.Message
import javax.inject.Inject


class MessageMapper @Inject constructor(){
    fun toDomain(entity: MessageEntity): Message {
        return Message(
            chatId = entity.chatId,
            senderName = entity.senderName,
            text = entity.text,
            timestamp = entity.timestamp,
            isFromCurrentUser = entity.isFromCurrentUser,
            isRead = entity.isRead
        )
    }

    fun toEntity(domain: Message): MessageEntity {
        return MessageEntity(
            chatId = domain.chatId,
            senderName = domain.senderName,
            text = domain.text,
            timestamp = domain.timestamp,
            isFromCurrentUser = domain.isFromCurrentUser,
            isRead = domain.isRead
        )
    }

    fun toDomainList(entities: List<MessageEntity>): List<Message> {
        return entities.map { toDomain(it) }
    }
}

//i mapper sono necessari, in genere quando i dati arrivano da un servizio usiamo il dto per la rispodts del servizio,
//usare una classe nel modulo data nel dominio anche se non viene utilizzata se bisogna processare un merge di dati presenti nel dat calss del servizio
//esterno possiamo fare un ma
//moduli indipendenti
//potevo farla in  un altro modo: usarlo dentro SendMessaageUseCase,