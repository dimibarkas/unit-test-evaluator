import {Task} from "../../../model/types";
import {Action, ActionType} from "../../actions/tasks";

const initialTaskListState = {
    isLoading: false,
    taskList: new Array<Task>()
}

interface ITaskListState {
    isLoading: boolean,
    taskList: Task[]
}

/**
 * The reducer which keeps the state of the list of tasks.
 * @param state
 * @param action
 */
export const tasks = (state: ITaskListState = initialTaskListState, action: Action): ITaskListState => {
    switch (action.type) {
        case ActionType.REQUEST_TASKS:
            return {
                ...state,
                isLoading: true,
            }
        case ActionType.RECEIVE_TASKS:
            return {
                ...state,
                isLoading: false,
                taskList: action.payload
            }
        default:
            return {
                ...state
            }
    }
}