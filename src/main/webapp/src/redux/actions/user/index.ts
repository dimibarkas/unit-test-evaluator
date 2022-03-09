import {fetchNewUser} from "../../../services";
import {User} from "../../../model/types";

export enum ActionType {
    REQUEST_USER = "REQUEST_USER",
    RECEIVE_USER = "RECEIVE_USER"
}

interface RequestUserAction {
    type: ActionType.REQUEST_USER,
}

interface ReceiveUserAction {
    type: ActionType.RECEIVE_USER,
    payload: User
}

export type Action = RequestUserAction | ReceiveUserAction

export const requestUser = (): RequestUserAction => ({
    type: ActionType.REQUEST_USER,
})

export const receiveUser = (user: User): ReceiveUserAction => ({
    type: ActionType.RECEIVE_USER,
    payload: user
})

const fetchUser = () => dispatch => {
    dispatch(requestUser())
    return fetchNewUser().then((user) => {
        console.log(user)
        dispatch(receiveUser(user))
    })
}

const shouldFetchNewUser = (state):boolean => {
    return state.user.user === null;
}

export const fetchUserIfNeeded = () => (dispatch, getState) => {
    if(shouldFetchNewUser(getState())) {
        return dispatch(fetchUser())
    }
}

