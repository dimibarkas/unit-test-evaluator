import {AuthCredentials, Progress} from "../../../model/types";
import {getProgressList} from "../../../services";
import {State} from "../../reducers";

export enum ActionType {
    REQUEST_PROGRESS_LIST = "REQUEST_PROGRESS_LIST",
    RECEIVE_PROGRESS_LIST = "RECEIVE_PROGRESS_LIST",
    STUDENT_HAS_PASSED_ALL_TASKS = "STUDENT_HAS_PASSED_ALL_TASKS"
}

interface RequestProgressListAction {
    type: ActionType.REQUEST_PROGRESS_LIST
}

interface ReceiveProgressListAction {
    type: ActionType.RECEIVE_PROGRESS_LIST,
    payload: Progress[],
}

interface StudentHasPassedAllTasks {
    type: ActionType.STUDENT_HAS_PASSED_ALL_TASKS
}

export type Action = RequestProgressListAction | ReceiveProgressListAction | StudentHasPassedAllTasks;

export const requestProgressList = (): RequestProgressListAction => ({
    type: ActionType.REQUEST_PROGRESS_LIST
})

export const receiveProgressList = (progressList: Progress[]): ReceiveProgressListAction => ({
    type: ActionType.RECEIVE_PROGRESS_LIST,
    payload: progressList
})

export const studentHasPassedAllTasks = (): StudentHasPassedAllTasks => ({
    type: ActionType.STUDENT_HAS_PASSED_ALL_TASKS
})

const shouldFetchProgressList = (state: State): boolean => {
    return !state.user.isFetching;
}

const checkIfAllTasksPassed = (progressList: Progress[]) => dispatch => {
    const containsUnfulfilledTasks = progressList.some((progress) =>
        progress.coveredBranches !== 100 && progress.coveredInstructions !== 100 && progress.hasAllMutationsPassed === false
    )
    if(!containsUnfulfilledTasks) {
        dispatch(studentHasPassedAllTasks())
    }
}

export const fetchProgressList = (authCredentials: AuthCredentials) => dispatch => {
    dispatch(requestProgressList())
    return getProgressList(authCredentials)
        .then((progress) => {
                dispatch(receiveProgressList(progress))
                dispatch(checkIfAllTasksPassed(progress))
            }
        );
}

export const fetchProgressListIfNeeded = (authCredentials: AuthCredentials) => (dispatch, getState) => {
    if (shouldFetchProgressList(getState())) {
        return dispatch(getProgressList(authCredentials))
    }
}