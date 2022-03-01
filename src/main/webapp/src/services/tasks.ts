import axios from "axios";
import {Task} from "../types/Task";

export const getAllTasks = async (): Promise<Task[]> => {
    let taskList: Task[];
    await axios.get("/api/tasks").then((res) => {
        // console.log(taskList);
        taskList = res.data.tasks;
    }).catch((error) => {
        console.log("tasks could not be loaded", error);
    });
    return taskList;
}
