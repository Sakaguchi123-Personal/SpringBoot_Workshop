import { describe, it, vi, expect } from 'vitest'
import axios from 'axios'
import { AxiosResponse } from 'axios'
import { DefaultTodoClient } from './TodoClient.ts'

describe('TodoClient Tests', () => {
    describe('GET todos', () => {
        it('should call /api/v1/todo', () => {
            const axiosGetSpy = vi.spyOn(axios, 'get').mockResolvedValue({
                status: 200,
                data: [],
            } as AxiosResponse)

            const client = new DefaultTodoClient()
            client.getTodos()

            expect(axiosGetSpy).toHaveBeenCalledWith('/api/v1/todo')
        })

        it('should return list of todo', async () => {
            vi.spyOn(axios, 'get').mockResolvedValue({
                status: 200,
                data: [
                    {
                        id: 1,
                        name: 'Learn Kotlin',
                        finished: false,
                    },
                ],
            } as AxiosResponse)

            const client = new DefaultTodoClient()
            const actual = await client.getTodos()

            expect(actual.length).toBe(1)
            expect(actual[0].id).toBe(1)
            expect(actual[0].name).toBe('Learn Kotlin')
            expect(actual[0].finished).toBe(false)
        })
    })

    describe('PUT todo', () => {
        it('should call /api/v1/todo/{id}', () => {
            const axiosPutSpy = vi.spyOn(axios, 'put').mockResolvedValue({
                status: 200,
            } as AxiosResponse)

            const requestBody = { name: 'Eat ra-men', finished: false }

            const client = new DefaultTodoClient()
            client.updateTodo(1, requestBody)

            expect(axiosPutSpy).toHaveBeenCalledWith(
                '/api/v1/todo/1',
                requestBody
            )
        })
    })
})
