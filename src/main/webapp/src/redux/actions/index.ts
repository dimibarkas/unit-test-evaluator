import {getAllTasks} from "../../services/tasks";
import {Task} from "../../types/Task";

export const REQUEST_TASKLIST = 'REQUEST_TASKLIST';
export const RECIEVE_TASKLIST = 'RECIEVE_TASKLIST';
export const SELECT_TASK = 'SELECT_TASK';
export const INVALIDATE_TASK = 'INVALIDATE_TASK';

export const selectTask = task => ({
    type: SELECT_TASK,
    task
})

export const invalidateTask = task => ({
    type: INVALIDATE_TASK,
    task
})

export const requestTasks = () => ({
    type: REQUEST_TASKLIST
})

export const recievePosts = (tasks: Task[]) => ({
    type: RECIEVE_TASKLIST,
    tasks: tasks
})

const fetchTasks = () => dispatch => {
    dispatch(requestTasks())
    return getAllTasks().then((tasks) => dispatch(recievePosts(tasks)))
}

const shouldFetchTasks = (state) => {
    const tasks = state.taskList
    if (tasks.length === 0) {
        return true
    }
    if (tasks.isFetching) {
        return false
    }
    return tasks.didInvalidate
}

export const fetchTasksIfNeeded = () => (dispatch, getState) => {
    if (shouldFetchTasks(getState())) {
        return dispatch(fetchTasks())
    }
}