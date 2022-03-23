import {Progress} from "../../../model/types";
import {getProgressList} from "../../../services";

export enum ActionType {
    REQUEST_PROGRESS_LIST = "REQUEST_PROGRESS_LIST",
    RECEIVE_PROGRESS_LIST = "RECEIVE_PROGRESS_LIST"
}

interface RequestProgressListAction {
    type: ActionType.REQUEST_PROGRESS_LIST
}

interface ReceiveProgressListAction {
    type: ActionType.RECEIVE_PROGRESS_LIST,
    payload: Progress[],
}

export type Action = RequestProgressListAction | ReceiveProgressListAction;

export const requestProgressList = (): RequestProgressListAction => ({
    type: ActionType.REQUEST_PROGRESS_LIST
})

export const receiveProgressList = (progressList: Progress[]): ReceiveProgressListAction => ({
    type: ActionType.RECEIVE_PROGRESS_LIST,
    payload: progressList
})

export const fetchProgressList = (userId: string) => dispatch => {
    dispatch(requestProgressList())
    return getProgressList(userId).then((progress) => dispatch(receiveProgressList(progress)));
}