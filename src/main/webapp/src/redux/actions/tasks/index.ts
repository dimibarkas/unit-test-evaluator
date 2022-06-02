import {AuthCredentials, Task} from "../../../model/types";
import {getAllTasks} from "../../../services";
import {authenticateStudent} from "../user";

export enum ActionType {
    REQUEST_TASKS = 'REQUEST_TASKS',
    RECEIVE_TASKS = 'RECEIVE_TASKS'
}

interface RequestTasksAction {
    type: ActionType.REQUEST_TASKS,
}

interface ReceiveTasksAction {
    type: ActionType.RECEIVE_TASKS,
    payload: Task[]
}

export type Action = RequestTasksAction | ReceiveTasksAction;

export const requestTasks = (): Action => ({
    type: ActionType.REQUEST_TASKS
})

export const receiveTasks = (tasks: Task[]) => ({
    type: ActionType.RECEIVE_TASKS,
    payload: tasks
})

const fetchTasks = (authCredentials: AuthCredentials) => dispatch => {
    dispatch(requestTasks())
    return getAllTasks(authCredentials)
        .then((tasks) => {
            dispatch(receiveTasks(tasks))
            dispatch(authenticateStudent(authCredentials))
        })
}

const shouldFetchTasks = (state): boolean => {
    return state.tasks.taskList.length === 0;
}

export const fetchTasksIfNeeded = (authCredentials: AuthCredentials) => (dispatch, getState) => {
    if (shouldFetchTasks(getState())) {
        return dispatch(fetchTasks(authCredentials))
    }
}