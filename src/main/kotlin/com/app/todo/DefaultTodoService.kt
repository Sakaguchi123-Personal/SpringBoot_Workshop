package com.app.todo

import org.springframework.stereotype.Service

@Service
class DefaultTodoService(val todoRepository: TodoRepository) : TodoService {

    //DBからデータを取得
    override fun getTodos() : List<Todo> {
        return todoRepository.findAll()
    }

    override fun postTodos(todo: Todo) {
        todoRepository.save(todo)
    }
    //post用サービスを作る　保存する処理.save()　→ポストもpatchもできる
    //インスタンス化したものをsaveにいれるとインサートできる
    //entity を使うと
    //    TodoEntity(1,"rinog",false)
}