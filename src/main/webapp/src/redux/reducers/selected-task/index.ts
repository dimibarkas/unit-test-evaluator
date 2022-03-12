import {Task} from "../../../model/types";
import {Action, ActionType} from "../../actions/selected-task";

interface ISelectedTaskState {
    task: Task,
}

const initialSelectedTaskState: ISelectedTaskState = {
    task: null,
}

/**
 * Reducer function for a selected task.
 * @param state current state
 * @param action dispatched action
 */
export const selectedTask = (state: ISelectedTaskState = initialSelectedTaskState, action: Action): ISelectedTaskState => {
    switch (action.type) {
        case ActionType.SELECT_TASK:
            return {
                ...state,
                task: action.payload
            }
        default:
            return state
    }
}
