import {AuthCredentials} from "../../../model/types";

export enum ActionType {
    AUTHENTICATE_STUDENT = "AUTHENTICATE_STUDENT",
    AUTHENTICATION_ERROR = "AUTHENTICATION_ERROR"
}

interface AuthenticateStudentAction {
    type: ActionType.AUTHENTICATE_STUDENT,
    payload: AuthCredentials
}

interface AuthenticationError {
    type: ActionType.AUTHENTICATION_ERROR
}

export type Action = AuthenticateStudentAction | AuthenticationError

export const authenticateStudent = (authCredentials: AuthCredentials): AuthenticateStudentAction => ({
    type: ActionType.AUTHENTICATE_STUDENT,
    payload: authCredentials
})

export const authenticationError = (): AuthenticationError => ({
    type: ActionType.AUTHENTICATION_ERROR
})

export const setAuthenticationError = () => (dispatch) => {
    console.log("setAuthenticationError Action thrown")
    return dispatch(authenticationError())
}