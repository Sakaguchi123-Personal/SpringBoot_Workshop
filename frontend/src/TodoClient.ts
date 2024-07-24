import { Todo, TodoUpdateRequest } from './TodoModel.ts'
import axios from 'axios'

export interface TodoClient {
    getTodos(): Promise<Todo[]>
    updateTodo(id: number, body: TodoUpdateRequest): Promise<void>
}

export class DefaultTodoClient implements TodoClient {
    async updateTodo(id: number, body: TodoUpdateRequest): Promise<void> {
        await axios.put(`/api/v1/todo/${id}`, body)
    }

    async getTodos(): Promise<Todo[]> {
        const { data } = await axios.get<Todo[]>('/api/v1/todo')
        return data
    }
}

// export class DefaultTodoClient implements TodoClient {
//     async getTodos(): Promise<Todo[]> {
//         const axiosResponse = await axios.get<Todo[]>('/api/v1/todo')
//         return axiosResponse.data
//     }
// }

// class DefaultTodoClient(val axios: Axios) {
//     fun getTodos: List<Todo> {
//         val axiosResponse = axios.get("/api/v1/todo")
//         return axiosResponse.data
//     }
// }
