package com.example.practicetodoapp

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class TodoViewModel: ViewModel() {
    private val _showCompleted = MutableStateFlow(false)
    val showCompleted: StateFlow<Boolean> = _showCompleted.asStateFlow()

    fun setShowCompleted(show: Boolean){
        _showCompleted.value = show
    }
    // ダミーのToDoリスト
    private val _todos = MutableStateFlow(
        listOf(
            Todo(id = 1, title = "ToDo1", memo = "Memo1"),
            Todo(id = 2, title = "ToDo2", memo = "Memo2"),
            Todo(id = 3, title = "ToDo3", memo = "Memo3"),
        )
    )

    val showTodos: StateFlow<List<Todo>> =
        combine(_todos, _showCompleted) { todos, showCompleted ->
        if (showCompleted) todos else todos.filter { !it.isCompleted }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun toggleComplete(todo: Todo){
        val updated = todo.copy(isCompleted = !todo.isCompleted)
        _todos.update { list ->
            list.map{if (it.id == todo.id) updated else it}
        }
    }

    fun saveTodo(todo: Todo){
        _todos.update { list ->
            if (todo.isNew){
                val nextId = (list.maxOfOrNull { it.id } ?: 0L) + 1L
                list + todo.copy(id = nextId)
            } else {
                list.map{if (it.id == todo.id) todo else it}
            }
        }
    }
}