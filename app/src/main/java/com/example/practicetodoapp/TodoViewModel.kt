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
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val _showCompleted = MutableStateFlow(false)
    val showCompleted: StateFlow<Boolean> = _showCompleted.asStateFlow()

    fun setShowCompleted(show: Boolean){
        _showCompleted.value = show
    }

    private val todoDao = TodoDatabase.getInstance(application.applicationContext).todoDao()

    private val _todos = todoDao.getAll()

    val showTodos: StateFlow<List<Todo>> =
        combine(_todos, _showCompleted) { todos, showCompleted ->
        if (showCompleted) todos else todos.filter { !it.isCompleted }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun toggleComplete(todo: Todo){
        val updated = todo.copy(isCompleted = !todo.isCompleted)
        saveTodo(updated)
    }

    fun saveTodo(todo: Todo){
        viewModelScope.launch {
            if (todo.isNew){
                val maxPos = todoDao.getMaxPosition() ?: 0
                val newTodo = todo.copy(position = maxPos + 1)
                todoDao.insert(newTodo)
            } else {
                todoDao.update(todo)
            }
        }
    }

    fun deletedCompletedTodos() {
        viewModelScope.launch {
            todoDao.deleteCompletedTodos()
        }
    }
}