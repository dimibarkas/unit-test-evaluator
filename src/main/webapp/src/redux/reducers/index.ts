import {combineReducers} from "redux";
import {Action, ActionType} from "../actions";
import {Task} from "../../types/Task";

const initialTaskListState = {
    isLoading: false,
    taskList:  new Array<Task>()
}

interface ITaskListState  {
    isLoading: boolean,
    taskList: Task[]
}

const tasks = (state:ITaskListState = initialTaskListState, action: Action) => {
    switch (action.type) {
        case ActionType.REQUEST_TASKS:
            return {
                ...state,
                isLoading: true,
            }
        case ActionType.RECEIVE_TASKS:
            return {
                ...state,
                isLoading: false,
                taskList: action.payload
            }
        default:
            return {
                ...state
            }
    }
}

const rootReducer = combineReducers({
    tasks,
})

export default rootReducer