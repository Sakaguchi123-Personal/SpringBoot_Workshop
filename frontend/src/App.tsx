import React, { useEffect, useState } from 'react'
import { TodoClient } from './TodoClient.ts'
import { Todo } from './TodoModel.ts'

import styles from './App.module.css'

interface Props {
    todoClient: TodoClient
}

const App: React.FC<Props> = ({ todoClient }) => {
    const [todo, setTodo] = useState<Todo[]>([])
    useEffect(() => {
        todoClient.getTodos().then((res) => {
            setTodo(res)
        })
    }, [])

    return (
        <div className={styles.container}>
            <div className={styles.title}>Todo App</div>
            <div className={styles.section} data-testid="remaining-todo">
                <div className={styles.sectionTitle}>Remaining Todo</div>
                <div className={styles.todos}>
                    {todo
                        .filter((t) => {
                            return t.finished === false
                        })
                        .map((ele) => {
                            return (
                                <div className={styles.row} key={ele.id}>
                                    <p>{ele.name}</p>
                                    <button
                                        className={styles.button}
                                        onClick={() => {
                                            todoClient
                                                .updateTodo(ele.id, {
                                                    name: ele.name,
                                                    finished: true,
                                                })
                                                .then(() => {
                                                    const todos = [...todo]
                                                    const updateTodo =
                                                        todos.find(
                                                            (t) =>
                                                                ele.id === t.id
                                                        )

                                                    if (!updateTodo) {
                                                        return
                                                    }

                                                    updateTodo.finished = true
                                                    setTodo(todos)
                                                })
                                        }}
                                    >
                                        Finish
                                    </button>
                                </div>
                            )
                        })}
                </div>
            </div>

            <div className={styles.section} data-testid="completed-todo">
                <div className={styles.sectionTitle}>Completed Todo</div>
                <div className={styles.todos}>
                    {todo
                        .filter((t) => {
                            return t.finished === true
                        })
                        .map((ele) => {
                            return (
                                <div key={ele.id}>
                                    <p>{ele.name}</p>
                                </div>
                            )
                        })}
                </div>
            </div>
        </div>
    )
}

export default App
