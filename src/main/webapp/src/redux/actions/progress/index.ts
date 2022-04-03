import {Progress} from "../../../model/types";
import {getProgressList} from "../../../services";
import {bool} from "prop-types";
import {State} from "../../reducers";

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

const shouldFetchProgressList = (state: State): boolean => {
    return !state.user.isFetching;
}

export const fetchProgressList = (userId: string) => dispatch => {
    dispatch(requestProgressList())
    return getProgressList(userId).then((progress) => dispatch(receiveProgressList(progress)));
}

export const fetchProgressListIfNeeded = () => (dispatch, getState) => {
    if(shouldFetchProgressList(getState())) {
        return dispatch(getProgressList(getState().user.user.id))
    }
}