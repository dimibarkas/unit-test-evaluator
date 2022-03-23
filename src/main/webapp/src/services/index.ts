import axios from "axios";
import {Submission, Task, SubmissionResult, User, Progress} from "../model/types";


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

export const submitCode = async (submission: Submission): Promise<SubmissionResult> => {
    let result: SubmissionResult;
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

export const getProgressList = async (userId: string): Promise<Progress[]> => {
    let result: Progress[];
    await axios.get("/api/progress", {
        params: {userId: userId}
    }).then((response) => {
        result = response.data?.progressList;
    }).catch((e) => {
        console.log(e);
        throw new Error("an error occurred while getting the progress list.");
    })
    return result;
}