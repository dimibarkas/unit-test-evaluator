import {combineReducers} from "redux";
import {selectedTask} from "./selected-task";
import {tasks} from "./tasks";

const rootReducer = combineReducers({
    tasks,
    selectedTask
})

export default rootReducer

export type State = ReturnType<typeof rootReducer>

