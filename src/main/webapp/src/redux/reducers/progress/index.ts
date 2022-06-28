import {Progress} from "../../../model/types";
import {Action, ActionType} from "../../actions/progress";

interface IProgressList {
    isLoading: boolean
    progressList: Progress[],
    hasAllTasksPassed: boolean,
    hasMinTasksPassed: boolean;
    hasQuit: boolean;
}

const initialProgressList: IProgressList = {
    isLoading: false,
    progressList: null,
    hasAllTasksPassed: false,
    hasMinTasksPassed: false,
    hasQuit: false
}

/**
 * Reducer function for the progress list.
 * @param state
 * @param action
 */
export const progress = (state: IProgressList = initialProgressList, action: Action): IProgressList => {
    switch (action.type) {
        case ActionType.REQUEST_PROGRESS_LIST:
            return {
                ...state,
                isLoading: true,
            }
        case ActionType.RECEIVE_PROGRESS_LIST:
            return {
                ...state,
                isLoading: false,
                progressList: action.payload
            }
        case ActionType.STUDENT_HAS_PASSED_ALL_TASKS:
            return {
                ...state,
                hasAllTasksPassed: true
            }
        case ActionType.STUDENT_HAS_PASSED_MIN_TASKS:
            return {
                ...state,
                hasMinTasksPassed: true
            }
        case ActionType.STUDENT_HAS_QUIT:
            return {
                ...state,
                hasQuit: true
            }
        default:
            return state
    }
}