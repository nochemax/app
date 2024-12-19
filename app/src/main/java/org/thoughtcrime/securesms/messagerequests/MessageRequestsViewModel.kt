package org.thoughtcrime.securesms.messagerequests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.thoughtcrime.securesms.database.model.ThreadRecord
import org.thoughtcrime.securesms.repository.ConversationRepository
import javax.inject.Inject
import org.python.util.PythonInterpreter
import org.python.core.PyObject
import javax.inject.Inject

@HiltViewModel
class MessageRequestsViewModel @Inject constructor(
    private val repository: ConversationRepository
) : ViewModel() {

    private lateinit var aiAgent: PyObject

    init {
        // Initialize Python interpreter and AI agent
        PythonInterpreter.initialize(System.getProperties(), System.getProperties(), arrayOf())
        val interpreter = PythonInterpreter()
        interpreter.exec("from ai_agent import AIAgent")
        aiAgent = interpreter.eval("AIAgent()")
    }

    fun filterMessageRequests(threads: List<ThreadRecord>): List<ThreadRecord> {
        val filterMethod = aiAgent.__getattr__("filter_requests")
        val threadData = threads.joinToString(separator = ",") { it.recipient.toString() }
        val filteredData = filterMethod.__call__(PyObject(threadData)).toString()
        return threads.filter { it.recipient.toString() in filteredData.split(",") }
    }

    fun blockMessageRequest(thread: ThreadRecord) = viewModelScope.launch {
        val recipient = thread.recipient
        if (recipient.isContactRecipient) {
            repository.setBlocked(recipient, true)
            deleteMessageRequest(thread)
        }
    }

    fun deleteMessageRequest(thread: ThreadRecord) = viewModelScope.launch {
        repository.deleteMessageRequest(thread)
    }

    fun deleteMessageRequest(thread: ThreadRecord) = viewModelScope.launch {
        val filteredThreads = filterMessageRequests(listOf(thread))
        if (filteredThreads.isNotEmpty()) {
            repository.deleteMessageRequest(thread)
        }
    }

    fun clearAllMessageRequests(block: Boolean) = viewModelScope.launch {
        repository.clearAllMessageRequests(block)
    }

}