import {fetchNewUser} from "../../../services";
import {AuthCredentials, RegistrationCredentials, User} from "../../../model/types";
import {State} from "../../reducers";

export enum ActionType {
    REQUEST_USER = "REQUEST_USER",
    RECEIVE_USER = "RECEIVE_USER",
    AUTHENTICATE_STUDENT = "AUTHENTICATE_STUDENT"
}

interface RequestUserAction {
    type: ActionType.REQUEST_USER,
}

interface ReceiveUserAction {
    type: ActionType.RECEIVE_USER,
    payload: User
}

interface AuthenticateStudentAction {
    type: ActionType.AUTHENTICATE_STUDENT,
    payload: AuthCredentials
}

export type Action = RequestUserAction | ReceiveUserAction | AuthenticateStudentAction

export const requestUser = (): RequestUserAction => ({
    type: ActionType.REQUEST_USER,
})

export const receiveUser = (user: User): ReceiveUserAction => ({
    type: ActionType.RECEIVE_USER,
    payload: user
})

export const authenticateStudent = (authCredentials: AuthCredentials): AuthenticateStudentAction => ({
    type: ActionType.AUTHENTICATE_STUDENT,
    payload: authCredentials
})

const fetchUser = () => dispatch => {
    dispatch(requestUser())
    return fetchNewUser().then((user) => {
        dispatch(receiveUser(user))
    })
}

const shouldFetchNewUser = (state):boolean => {
    return state.user.user === null;
}

export const fetchUserIfNeeded = () => (dispatch, getState) => {
    if (shouldFetchNewUser(getState())) {
        return dispatch(fetchUser())
    }
}