/**
 * This type describes a task.
 */
export type Task = {
    id: number;
    name: string;
    description: string;
    targetDescription: string;
    encodedFile: string;
    encodedTestTemplate: string;
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
export type TestResult = {
    output: string,
    report: any,
    summary: BuildSummary
}

export type Submission = {
    taskId: number,
    encodedTestContent: string,
}

/**
 * This type represents a simple appuser.
 */
export type User = {
    id: string;
    createdAt: Date
}