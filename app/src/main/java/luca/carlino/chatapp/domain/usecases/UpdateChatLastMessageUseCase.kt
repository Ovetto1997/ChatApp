package luca.carlino.chatapp.domain.usecases

import luca.carlino.chatapp.data.repository.abstraction.ChatRepository
import javax.inject.Inject

class UpdateChatLastMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        chatId: Long,
        lastMessage: String,
        timestamp: Long
    ) {
        chatRepository.updateChatLastMessage(
            chatId = chatId,
            lastMessage = lastMessage,
            timestamp = timestamp
        )
    }
}

//live data, xml, activity, fragment, live data costruiti sulla base lifecycle aware, costruiti sulll'api di kotlin
//fa si che i live data siano adatti per aggiornare la UI in xml
// attività semplice con mini flusso di login e onboarding: utente inserisce user name e password che mostri in maniera opporttuna errori,
//e mostri attraverso uno snackbar, password errata, user name sbagliato, se la login va a buon fine l'utente entra in un flusso onboarding in
// cui sceglie il nick name e dati personali. Come quarto step ingresso una home con "Benvenuto nome e immmagine" immagini possono essere fatte nella cartella drwable,
//in questa attività utilizzo due build flavor, avere un flavor produzione, un flavor mock. se guardo lo scaffolding vedo come fare.
//no product flavor solo in locale, solo il mock, database locale, app deve essere utilizzata con metodi vefri proprio, login il flow della login sia una resource di boolean
//usare il databinding viewbiding, soluzioni che permettono di passare all'xml più dinamico, con databinding lo riesco a rendere più dinamico a seconda dello stato che linpasso
//passare lifling data all'xml, navigazione jetpack navigation, focus rendere la mini attività scalabile che funzioni anche con servizio qualora vennisse messa in produzione,
// i repo e data source siano pensate come dovessero a
//in produzione. partire dallo studio databinding, view biding richiama dei riferimetni delle view di un dato xml, layot_main, biding generato nell'activiti, mainActivity biding
// permette di instanziare la view dell'activity, biding parametizzarlo in modo tale che lo possa chiamare nell'on create, nell'xml ho una viewbutton, nella classe activity, biding.button
//nello scaffoldinfg implementazione a seconda del product flavor selezionato, e un implemetazione di una ambiente dev e m
//dev debug e dev release sono build type di default, dev e mock quelli che mi servono
