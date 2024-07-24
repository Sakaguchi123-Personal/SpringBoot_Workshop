export type Todo = {
    id: number
    name: string
    finished: boolean
}

export type TodoUpdateRequest = {
    name: string
    finished: boolean
}
