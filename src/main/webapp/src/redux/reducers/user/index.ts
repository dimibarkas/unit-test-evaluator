import {ActionType, Action} from "../../actions/user";
import {User} from "../../../model/types";

interface IUser {
    isFetching: boolean;
    user: User;
}

const initialUserState: IUser = {
    isFetching: false,
    user: null,
}

export const user = (state: IUser = initialUserState, action: Action): IUser => {
    switch (action.type) {
        case ActionType.REQUEST_USER:
            return {
                ...state,
                isFetching: true
            }
        case ActionType.RECEIVE_USER:
            console.log(action)
            return {
                ...state,
                isFetching: false,
                user: action.payload
            }
        default:
            return state
    }
}