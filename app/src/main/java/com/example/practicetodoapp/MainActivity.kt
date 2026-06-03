package com.example.practicetodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicetodoapp.ui.theme.PracticeToDoAppTheme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeToDoAppTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    val viewModel: TodoViewModel = viewModel()
    val showTodos by viewModel.showTodos.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TodoTopBar(
                showCompleted = false,
                onToggleShowCompleted = { },
                onDeleteCompleted = { },
            )
        },
        floatingActionButton = {
            TodoActionButton {
                // 後で処理を書く
            }
        }
        ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TodoList(
                todos = showTodos,
                onToggleComplete = {viewModel.toggleComplete(it)},
                onEdit = { },
            )
        }
    }
}

@Composable
fun TodoList(
    todos: List<Todo>,
    onToggleComplete: (Todo) -> Unit,
    onEdit: (Todo) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = Modifier) {
        items (
            items = todos,
            key = {it.id}
        ) {
                todo ->
            TodoCard(
                todo = todo,
                onToggleComplete = onToggleComplete,
                onEdit = onEdit
            )
        }
    }
}

@Composable
fun TodoCard(
    todo: Todo,
    onToggleComplete: (Todo) -> Unit,
    onEdit: (Todo) -> Unit,
){
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clickable{onEdit(todo)}
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ){
            Checkbox(
                checked = todo.isCompleted,
                onCheckedChange = {onToggleComplete(todo)},
                modifier = Modifier.padding(8.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = todo.title, fontSize = 20.sp)
                Text(
                    text = todo.memo,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun TodoActionButton(onNewTodo: () -> Unit) {
    FloatingActionButton(onClick = onNewTodo) {
        Icon(Icons.Filled.Add, "add todo")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopBar (
    modifier: Modifier = Modifier,
    showCompleted: Boolean,
    onToggleShowCompleted: (Boolean) -> Unit,
    onDeleteCompleted: () -> Unit,
){
    TopAppBar(
        modifier = modifier,
        title = {Text("ToDo")},
        actions = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("完了を表示", fontSize = 14.sp)
                Switch(
                    checked = showCompleted,
                    onCheckedChange = onToggleShowCompleted
                )
                IconButton(
                    onClick = onDeleteCompleted
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "完了したToDoを削除")
                }
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    PracticeToDoAppTheme {
        Main()
    }
}