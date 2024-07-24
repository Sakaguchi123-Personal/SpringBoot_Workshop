import { render, screen, within } from '@testing-library/react'
import App from './App.tsx'
import { it, expect, describe } from 'vitest'
import { TodoClient } from './TodoClient.ts'
import { SpyStubTodoClient } from './SpyStubTodoClient.ts'
import { userEvent } from '@testing-library/user-event'

describe('App Tests', () => {
    it('should display title', () => {
        renderApp()

        expect(screen.getByText('Todo App')).toBeInTheDocument()
    })

    it('should display section titles', () => {
        renderApp()

        expect(screen.getByText('Remaining Todo')).toBeInTheDocument()
        expect(screen.getByText('Completed Todo')).toBeInTheDocument()
    })

    it('should call TodoClient getTodos', () => {
        const spyStubTodoClient = new SpyStubTodoClient()
        renderApp(spyStubTodoClient)

        expect(spyStubTodoClient.getTodos).toHaveBeenCalledTimes(1)
    })

    it('should display todo under remaining todo when finished is false', async () => {
        const spyStubTodoClient = new SpyStubTodoClient()
        spyStubTodoClient.getTodos.mockResolvedValue([
            {
                id: 1,
                name: 'Learn Kotlin',
                finished: false,
            },
            {
                id: 2,
                name: 'Learn React',
                finished: true,
            },
        ])

        renderApp(spyStubTodoClient)

        const remainingSection = screen.getByTestId('remaining-todo')

        expect(
            await within(remainingSection).findByText('Learn Kotlin')
        ).toBeInTheDocument()
        expect(
            within(remainingSection).getByRole('button', { name: 'Finish' })
        ).toBeInTheDocument()
        expect(
            within(remainingSection).queryByText('Learn React')
        ).not.toBeInTheDocument()
    })

    it('should display todo under completed todo when finished is true', async () => {
        const spyStubTodoClient = new SpyStubTodoClient()
        spyStubTodoClient.getTodos.mockResolvedValue([
            {
                id: 1,
                name: 'Learn Kotlin',
                finished: false,
            },
            {
                id: 2,
                name: 'Learn React',
                finished: true,
            },
        ])

        renderApp(spyStubTodoClient)

        const completedSection = screen.getByTestId('completed-todo')
        expect(
            await within(completedSection).findByText('Learn React')
        ).toBeInTheDocument()
        expect(
            within(completedSection).queryByText('Learn Kotlin')
        ).not.toBeInTheDocument()
    })

    it('should call todoClient updateTodo when finish button is clicked', async () => {
        const spyStubTodoClient = new SpyStubTodoClient()
        spyStubTodoClient.getTodos.mockResolvedValue([
            {
                id: 1,
                name: 'Learn Kotlin',
                finished: false,
            },
        ])

        renderApp(spyStubTodoClient)

        const finishButton = await screen.findByRole('button', {
            name: 'Finish',
        })

        await userEvent.click(finishButton)

        expect(spyStubTodoClient.updateTodo).toHaveBeenCalledWith(1, {
            name: 'Learn Kotlin',
            finished: true,
        })
    })

    it('should display todo under completed todo when finish button is clicked', async () => {
        const spyStubTodoClient = new SpyStubTodoClient()
        spyStubTodoClient.getTodos.mockResolvedValue([
            {
                id: 1,
                name: 'Learn java',
                finished: false,
            },
        ])

        renderApp(spyStubTodoClient)

        const finishButton = await screen.findByRole('button', {
            name: 'Finish',
        })
        await userEvent.click(finishButton)

        const completedSection = screen.getByTestId('completed-todo')
        expect(
            await within(completedSection).findByText('Learn java')
        ).toBeInTheDocument()
    })
})

const renderApp = (spyStubTodoClient: TodoClient = new SpyStubTodoClient()) => {
    render(<App todoClient={spyStubTodoClient} />)
}
