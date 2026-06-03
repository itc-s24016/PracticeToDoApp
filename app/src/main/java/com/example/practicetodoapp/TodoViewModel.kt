package com.example.practicetodoapp

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    // ダミーのToDoリスト
    private val _todos = MutableStateFlow(
        listOf(
            Todo(id = 1, title = "ToDo1", memo = "Memo1"),
            Todo(id = 2, title = "ToDo2", memo = "Memo2"),
            Todo(id = 3, title = "ToDo3", memo = "Memo3"),
        )
    )
    val showTodos = _todos.asStateFlow()
}