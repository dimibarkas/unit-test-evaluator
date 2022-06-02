import axios from "axios";
import {
    Submission,
    Task,
    SubmissionResult,
    Progress,
    RegistrationCredentials,
    AuthCredentials
} from "../model/types";
import {onFulfilled, onRejected} from "../axios-interceptors";

const instance = axios.create();
instance.interceptors.response.use(onFulfilled, onRejected);

export const getAllTasks = async (authCredentials: AuthCredentials): Promise<Task[]> => {
    const {authKey, studentId} = authCredentials;
    let taskList: Task[];
    await instance
        .get(
            `/api/${studentId}/tasks`,
            {
                params: {authKey: authKey}
            }).then((res) => {
            taskList = res.data?.tasks;
        }).catch((e) => {
            console.log(e);
            throw new Error("tasks could not be loaded");
        });
    return taskList;

}

export const submitCode = async (authCredentials: AuthCredentials, submission: Submission): Promise<SubmissionResult> => {
    let result: SubmissionResult;
    const {taskId, encodedTestContent} = submission;
    const {authKey, studentId} = authCredentials;
    await instance.post(`/api/${studentId}/evaluate`, {
        taskId: taskId,
        encodedTestContent: encodedTestContent
    }, {params: {authKey: authKey}})
        .then((response) => {
            result = response.data;
        }).catch((e) => {
            console.log(e);
            throw new Error("an error occurred during code submission");
        })
    return result;
}

export const getProgressList = async (authCredentials: AuthCredentials): Promise<Progress[]> => {
    let result: Progress[];
    const {authKey, studentId} = authCredentials;
    await instance.get(`/api/${studentId}/progress`, {
        params: {authKey: authKey}
    }).then((response) => {
        result = response.data?.progressList;
    }).catch((e) => {
        console.log(e);
        throw new Error("an error occurred while getting the progress list.");
    })
    return result;
}

export const requestAuthKey = async (registrationCredentials: RegistrationCredentials): Promise<void> => {
    const {id, email} = registrationCredentials;
    await instance.post(`/api/${id}/register`,
        {},
        {
            params: {email: email}
        }).catch((e) => {
        console.log(e);
        throw new Error("an error occurred while requesting authentication mail.");
    })
}
export const submitResults = async ():Promise<void> => {

}