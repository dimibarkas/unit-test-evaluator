import {combineReducers} from "redux";
import {selectedTask} from "./selected-task";
import {tasks} from "./tasks";
import {user} from "./user";
import {progress} from "./progress";

const rootReducer = combineReducers({
    tasks,
    selectedTask,
    user,
    progress
})

export default rootReducer

export type State = ReturnType<typeof rootReducer>

