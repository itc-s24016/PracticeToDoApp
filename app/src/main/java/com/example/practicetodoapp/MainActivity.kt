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
        )
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