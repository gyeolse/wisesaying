package com.example.androidsample.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsample.data.model.Todo
import com.example.androidsample.domain.repository.LoginRepository
import com.example.androidsample.domain.repository.TodoRepository
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _items = mutableStateOf(emptyList<Todo>())
    val items: State<List<Todo>> = _items

    private var recentlyDeleteTodo: Todo? = null
    init {
        viewModelScope.launch {
            todoRepository.observeTodos()
                .collect { todos ->
                    _items.value = todos
                }
        }
    }
    fun addTodo(text: String) {
        viewModelScope.launch {
            todoRepository.addTodo(Todo(title = text))
        }
    }

    fun toggle(index: Int) {
        val todo = _items.value.find { Todo -> Todo.uid == index }

        // 만약 todo가 있다면. nullable 체크 ?
        todo?.let {
            viewModelScope.launch {
                todoRepository.updateTodo(it.copy(isDone = !todo.isDone).apply {
                    this.uid = it.uid
                })
            }
        }
    }

    fun delete(index: Int) {
        val todo = _items.value.find { todo -> todo.uid == index }
        todo?.let {
            viewModelScope.launch {
                todoRepository.deleteTodo(it)
                recentlyDeleteTodo = it
            }
        }
    }

    fun restoreTodo() {
        viewModelScope.launch {
            // recentlyDeleteTodo 가 nullable이라서 이걸 처리해줘야함.
            // elvis 연산자 사용 return@launch 라고 하면 viewModelScope에 있었던 @launch가 취소된다.
            todoRepository.addTodo(recentlyDeleteTodo ?: return@launch)
            recentlyDeleteTodo = null
        }
    }

    fun createKakaoToken() {
        viewModelScope.launch {
            loginRepository.createKakaoToken()
        }
    }
}