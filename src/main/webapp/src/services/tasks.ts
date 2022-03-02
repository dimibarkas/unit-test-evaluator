import axios from "axios";
import {Task} from "../model/types";

export const getAllTasks = async (): Promise<Task[]> => {
    let taskList: Task[];
    await axios.get("/api/tasks").then((res) => {
        // console.log(taskList);
        taskList = res.data.tasks;
    }).catch((error) => {
        console.error("tasks could not be loaded", error);
    });
    return taskList;
}
