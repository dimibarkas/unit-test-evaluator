import {Progress} from "../../../model/types";
import {Action, ActionType} from "../../actions/progress";

interface IProgressList {
    isLoading: boolean
    progressList: Progress[]
}

const initialProgressList: IProgressList = {
    isLoading: false,
    progressList: null,
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
        default:
            return state
    }
}