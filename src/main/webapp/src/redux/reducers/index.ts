import {combineReducers} from "redux";
import {INVALIDATE_TASK, RECIEVE_TASKLIST, REQUEST_TASKLIST, SELECT_TASK} from "../actions";
import {Task} from "../../types/Task";

const selectedTask = (state: Task = null, action) => {
    switch (action.type) {
        case SELECT_TASK:
            return action.task
        default:
            return state
    }
}

const tasks = (state =
                   {
                       isFetching: false,
                       didInvalidate: false,
                       tasks: []
                   },
               action) => {
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
        case RECIEVE_TASKLIST:
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

const taskList = (state = [], action) => {
    switch (action.type) {
        case INVALIDATE_TASK:
        case RECIEVE_TASKLIST:
        case REQUEST_TASKLIST:
            return {
                ...state,
                fetchedList: tasks(state[action.task], action)
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