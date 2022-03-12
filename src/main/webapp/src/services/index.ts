import axios from "axios";
import {Submission, Task, TestResult, User} from "../model/types";



export const getAllTasks = async (): Promise<Task[]> => {
    let taskList: Task[];
    await axios.get("/api/tasks").then((res) => {
        taskList = res.data?.tasks;
    }).catch((e) => {
        console.log(e);
        throw new Error("tasks could not be loaded");
    });
    return taskList;

}

export const submitCode = async (submission: Submission): Promise<TestResult> => {
    let result: TestResult;
    await axios.post("/api/evaluate", submission)
        .then((response) => {
            result = response.data;
        }).catch((e) => {
            console.log(e);
            throw new Error("an error occurred during code submission");
        })
    return result;
}

export const fetchNewUser = async (): Promise<User> => {
    let user: User;
    await axios.post("/api/user")
        .then((response) => {
            user = {id: response.data?.id, createdAt: response.data?.createdAt};
        }).catch((e) => {
            console.log(e)
            throw new Error("an error occurred while fetching user");
        })
    return user;
}