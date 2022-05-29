import {Task} from "../../../model/types";
import {getAllTasks} from "../../../services";
import {State} from "../../reducers";
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

const fetchTasks = (studentNumber: string, authKey: string) => dispatch => {
    dispatch(requestTasks())
    return getAllTasks(studentNumber, authKey)
        .then((tasks) => {
            dispatch(receiveTasks(tasks))
            dispatch(authenticateStudent({studentNumber: studentNumber, authKey: authKey}))
        })
}

const shouldFetchTasks = (state): boolean => {
    return state.tasks.taskList.length === 0;
}

export const fetchTasksIfNeeded = (studentNumber: string, authKey: string) => (dispatch, getState) => {
    if (shouldFetchTasks(getState())) {
        return dispatch(fetchTasks(studentNumber, authKey))
    }
}