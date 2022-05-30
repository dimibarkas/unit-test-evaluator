import {Action, ActionType} from "../../actions/user";
import {Student} from "../../../model/types";

interface IUser {
    isFetching: boolean;
    student: Student;
    isAuthenticated: boolean;
    id: string;
    authKey: string;
    authenticationError: boolean;
}

const initialUserState: IUser = {
    isFetching: false,
    student: null,
    isAuthenticated: false,
    id: null,
    authKey: null,
    authenticationError: false
}

export const user = (state: IUser = initialUserState, action: Action): IUser => {
    switch (action.type) {
        case ActionType.AUTHENTICATE_STUDENT:
            return {
                ...state,
                isAuthenticated: true,
                id: action.payload.studentId,
                authKey: action.payload.authKey
            }
        case ActionType.AUTHENTICATION_ERROR:
            return {
                ...state,
                isAuthenticated: false,
                authenticationError: true,
                id: null,
                authKey: null,
                student: null
            }
        default:
            return state
    }
}