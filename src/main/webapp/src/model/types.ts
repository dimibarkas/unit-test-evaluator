/**
 * This type describes a task.
 */
export type Task = {
    id: number;
    name: string;
    description: string;
    shortDescription: string;
    targetDescription: string;
    encodedFile: string;
    encodedTestTemplate: string;
    hint: string;
}

/**
 * This enum represents the summary of a build.
 */
export enum BuildSummary {
    BUILD_FAILED = "BUILD_FAILED",
    TESTS_FAILED = "TESTS_FAILED",
    BUILD_SUCCESSFUL = "BUILD_SUCCESSFUL",
}

/**
 * This type represents the type of object wich is returned when a task is submitted.
 */
export type SubmissionResult = {
    output: string,
    report: any,
    summary: BuildSummary
    feedback: string;
}

export type Submission = {
    taskId: number,
    encodedTestContent: string,
    studentId: string;
}

/**
 * This type represents a simple appuser.
 */
export type Student = {
    id: string;
    firstname: string;
    lastname: string;
    createdAt: string;
    authKey: string;
}



export type Progress = {
    id: number;
    coveredInstructions: number;
    coveredBranches: number;
    hasAllMutationsPassed: boolean;
}

export interface RegistrationCredentials {
    id: string;
    email: string;
}

export interface AuthCredentials {
    studentId: string;
    authKey: string;
}

