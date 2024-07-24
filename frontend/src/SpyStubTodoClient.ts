import { TodoClient } from './TodoClient.ts'
import { vi } from 'vitest'

export class SpyStubTodoClient implements TodoClient {
    getTodos = vi.fn().mockResolvedValue([])

    updateTodo = vi.fn().mockResolvedValue(null)
}
