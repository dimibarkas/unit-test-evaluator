import {Action, ActionType} from "../../actions/user";
import {User} from "../../../model/types";

interface IUser {
    isFetching: boolean;
    user: User;
    isAuthenticated: boolean;
    id: string;
    authKey: string;
}

const initialUserState: IUser = {
    isFetching: false,
    user: null,
    isAuthenticated: false,
    id: null,
    authKey: null
}

export const user = (state: IUser = initialUserState, action: Action): IUser => {
    switch (action.type) {
        case ActionType.REQUEST_USER:
            return {
                ...state,
                isFetching: true
            }
        case ActionType.RECEIVE_USER:
            return {
                ...state,
                isFetching: false,
                user: action.payload
            }
        case ActionType.AUTHENTICATE_STUDENT:
            return {
                ...state,
                isAuthenticated: true,
                id: action.payload.studentNumber,
                authKey: action.payload.authKey
            }
        default:
            return state
    }
}