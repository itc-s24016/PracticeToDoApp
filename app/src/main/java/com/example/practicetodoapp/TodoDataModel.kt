package com.example.practicetodoapp
// １件分のToDoを表すデータ構造
data class Todo(
    val id: Long = 0, // キー
    val title: String, // タイトル
    val memo: String, // メモ
    val isCompleted: Boolean = false, // 完了状態
    val position: Int = 0 // 表示順
){
    val isNew: Boolean // 新規のToDoを表す
        get() = id == 0L
}