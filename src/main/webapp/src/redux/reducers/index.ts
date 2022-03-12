import {combineReducers} from "redux";
import {selectedTask} from "./selected-task";
import {tasks} from "./tasks";
import {user} from "./user";

const rootReducer = combineReducers({
    tasks,
    selectedTask,
    user
})

export default rootReducer

export type State = ReturnType<typeof rootReducer>

