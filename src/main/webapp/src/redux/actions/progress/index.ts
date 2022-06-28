import {AuthCredentials, Progress} from "../../../model/types";
import {getProgressList} from "../../../services";

export enum ActionType {
    REQUEST_PROGRESS_LIST = "REQUEST_PROGRESS_LIST",
    RECEIVE_PROGRESS_LIST = "RECEIVE_PROGRESS_LIST",
    STUDENT_HAS_PASSED_ALL_TASKS = "STUDENT_HAS_PASSED_ALL_TASKS",
    STUDENT_HAS_PASSED_MIN_TASKS = "STUDENT_HAS_PASSED_MIN_TASKS",
    STUDENT_HAS_QUIT = "STUDENT_HAS_QUIT"
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

interface StudentHasPassedMinTasks {
    type: ActionType.STUDENT_HAS_PASSED_MIN_TASKS
}

interface StudentHasQuit {
    type: ActionType.STUDENT_HAS_QUIT
}

export type Action =
    RequestProgressListAction
    | ReceiveProgressListAction
    | StudentHasPassedAllTasks
    | StudentHasPassedMinTasks
    | StudentHasQuit;

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

export const studentHasPassedMinTasks = (): StudentHasPassedMinTasks => ({
    type: ActionType.STUDENT_HAS_PASSED_MIN_TASKS
})

export const studentHasQuit = (): StudentHasQuit => ({
    type: ActionType.STUDENT_HAS_QUIT
})


const checkIfMinTasksPassed = (progressList: Progress[]) => dispatch => {
    let numTasksPassed = 0;
    progressList.forEach((progress) => {
        if (progress.coveredBranches === 100 &&
            progress.coveredInstructions === 100 &&
            progress.hasAllMutationsPassed) {
            numTasksPassed++;
        }
    })
    if (numTasksPassed >= 3) {
        dispatch(studentHasPassedMinTasks())
    }
}

const checkIfAllTasksPassed = (progressList: Progress[]) => dispatch => {
    const containsUnfulfilledTasks = progressList.some((progress) =>
        progress.coveredBranches !== 100 && progress.coveredInstructions !== 100 && progress.hasAllMutationsPassed === false
    )
    if (!containsUnfulfilledTasks) {
        dispatch(studentHasPassedAllTasks())
    }
}

export const fetchProgressList = (authCredentials: AuthCredentials) => dispatch => {
    dispatch(requestProgressList())
    return getProgressList(authCredentials)
        .then((progress) => {
                dispatch(receiveProgressList(progress))
                dispatch(checkIfMinTasksPassed(progress))
                dispatch(checkIfAllTasksPassed(progress))
            }
        );
}

export const fetchProgressListIfNeeded = (authCredentials: AuthCredentials) => (dispatch) => {
    return dispatch(getProgressList(authCredentials))
}
