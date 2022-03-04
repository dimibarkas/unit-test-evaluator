import {Task} from "../../../model/types";

export enum ActionType {
    SELECT_TASK = "SELECT_TASK"
}

interface SelectTaskAction {
    type: ActionType.SELECT_TASK,
    payload: Task
}

export type Action = SelectTaskAction

export const selectTask = (task: Task): Action => ({
    type: ActionType.SELECT_TASK,
    payload: task
})