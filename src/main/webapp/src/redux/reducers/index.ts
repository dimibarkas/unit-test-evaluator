import {AnyAction, combineReducers} from "redux";
import {INVALIDATE_TASK, RECEIVE_TASKLIST, REQUEST_TASKLIST, SELECT_TASK} from "../actions";
import {Task} from "../../types/Task";

const selectedTask = (state: Task = null, action) => {
    switch (action.type) {
        case SELECT_TASK:
            return action.task
        default:
            return state
    }
}

// interface ITaskListState {
//     isFetching: boolean;
//     didInvalidate: false;
//     tasks: Task[];
// }
//
// const initialTaskListState: ITaskListState = {
//     isFetching: false,
//     didInvalidate: false,
//     tasks: [],
// }

//TODO: typing
const taskList = (state = [], action: AnyAction) => {
    console.log(action.payload)
    switch (action.type) {
        case INVALIDATE_TASK:
            return {
                ...state,
                didInvalidate: true
            }
        case REQUEST_TASKLIST:
            return {
                ...state,
                isFetching: true,
                didInvalidate: false
            }
        case RECEIVE_TASKLIST:
            return {
                ...state,
                isFetching: false,
                didInvalidate: false,
                tasks: action.tasks,
            }
        default:
            return state
    }
}

const rootReducer = combineReducers({
    selectedTask,
    taskList
})

export default rootReducer