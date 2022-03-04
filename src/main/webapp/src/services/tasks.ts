import axios from "axios";
import {EvaluationRequest, Task, TestResult} from "../model/types";

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

export const submitCode = async (request: EvaluationRequest): Promise<TestResult> => {
    let result: TestResult;
    await axios.post<TestResult>("/api/evaluate", request)
        .then((response) => {
            result = response.data;
        }).catch(() => {
            throw new Error("an error occurred during code submission");
        })
    return result;
}