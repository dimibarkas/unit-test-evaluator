import axios from 'axios'
import {fetchNewUser, getAllTasks, submitCode} from "../index";
import {BuildSummary, Submission, TestResult} from "../../model/types";

jest.mock('axios')
const mockedAxios = axios as jest.Mocked<typeof axios>;

const data = {
    "tasks": [
        {
            "id": "1",
            "name": "Sortieralgorithmus I: InsertionSort",
            "description": "Der Sortieralgorithmus InsertionSort in Java implementiert.",
            "targetDescription": "Erreichen Sie eine Line-Coverage von 100% für die gesamte Klasse.",
            "encodedFile": "cGFja2FnZSBjb20udGVzdC5hcHA7CgpwdWJsaWMgY2xhc3MgSW5zZXJ0aW9uU29ydCB7CgogICAgdm9pZCBzb3J0KGludFtdIGFycikgewogICAgICAgIGZvciAoaW50IGkgPSAxOyBpIDwgYXJyLmxlbmd0aDsgKytpKSB7CiAgICAgICAgICAgIGludCBrZXkgPSBhcnJbaV07CiAgICAgICAgICAgIGludCBqID0gaSAtIDE7CgogICAgICAgICAgICAvKiBNb3ZlIGVsZW1lbnRzIG9mIGFyclswLi5pLTFdLCB0aGF0IGFyZQogICAgICAgICAgICAgICBncmVhdGVyIHRoYW4ga2V5LCB0byBvbmUgcG9zaXRpb24gYWhlYWQKICAgICAgICAgICAgICAgb2YgdGhlaXIgY3VycmVudCBwb3NpdGlvbiAqLwogICAgICAgICAgICB3aGlsZSAoaiA+PSAwICYmIGFycltqXSA+IGtleSkgewogICAgICAgICAgICAgICAgYXJyW2ogKyAxXSA9IGFycltqXTsKICAgICAgICAgICAgICAgIGogPSBqIC0gMTsKICAgICAgICAgICAgfQogICAgICAgICAgICBhcnJbaiArIDFdID0ga2V5OwogICAgICAgIH0KICAgIH0KfQo=",
            "encodedTestTemplate": "cGFja2FnZSBjb20udGVzdC5hcHA7CgppbXBvcnQgb3JnLmp1bml0LlRlc3Q7CgppbXBvcnQgc3RhdGljIG9yZy5qdW5pdC5Bc3NlcnQuKjsKCnB1YmxpYyBjbGFzcyBJbnNlcnRpb25Tb3J0VGVzdCB7CgogICAgQFRlc3QKICAgIHB1YmxpYyB2b2lkIHNvcnQoKSB7CiAgICB9Cn0="
        },
        {
            "id": "2",
            "name": "Sortieralgorithmus II: BubbleSort",
            "description": "Der Sortieralgorithmus BubbleSort in Java implementiert.",
            "targetDescription": "Erreichen Sie eine Test-Coverage von 100%.",
            "encodedFile": "cGFja2FnZSBjb20udGVzdC5hcHA7CgpwdWJsaWMgY2xhc3MgQnViYmxlU29ydCB7CgogICAgdm9pZCBidWJibGVTb3J0KGludFtdIGFycikKICAgIHsKICAgICAgICBpbnQgbiA9IGFyci5sZW5ndGg7CiAgICAgICAgZm9yIChpbnQgaSA9IDA7IGkgPCBuLTE7IGkrKykKICAgICAgICAgICAgZm9yIChpbnQgaiA9IDA7IGogPCBuLWktMTsgaisrKQogICAgICAgICAgICAgICAgaWYgKGFycltqXSA+IGFycltqKzFdKQogICAgICAgICAgICAgICAgewogICAgICAgICAgICAgICAgICAgIC8vIHN3YXAgYXJyW2orMV0gYW5kIGFycltqXQogICAgICAgICAgICAgICAgICAgIGludCB0ZW1wID0gYXJyW2pdOwogICAgICAgICAgICAgICAgICAgIGFycltqXSA9IGFycltqKzFdOwogICAgICAgICAgICAgICAgICAgIGFycltqKzFdID0gdGVtcDsKICAgICAgICAgICAgICAgIH0KICAgIH0KfQo=",
            "encodedTestTemplate": "cGFja2FnZSBjb20udGVzdC5hcHA7CgppbXBvcnQgb3JnLmp1bml0LlRlc3Q7CgppbXBvcnQgc3RhdGljIG9yZy5qdW5pdC5Bc3NlcnQuKjsKCnB1YmxpYyBjbGFzcyBCdWJibGVTb3J0VGVzdCB7CgogICAgQFRlc3QKICAgIHB1YmxpYyB2b2lkIHNvcnQoKSB7CiAgICB9Cn0="
        }
    ]
};

const testResult: TestResult = {
    output: "testtest",
    report: "report",
    summary: BuildSummary.BUILD_SUCCESSFUL
}

const testSubmission: Submission = {
    taskId: 1,
    encodedTestContent: "encodedTest"
}

const testUserId = "02ae07b6-f8fa-49ba-b338-545cff81512a"

beforeEach(() => {
    jest.clearAllMocks();
});


describe('Service', () => {
    it('fetches tasks from backend', async () => {

        mockedAxios.get.mockImplementationOnce(() => Promise.resolve(data));
        await getAllTasks();
        expect(mockedAxios.get).toHaveBeenCalled();
        expect(mockedAxios.get).toHaveBeenCalledWith("/api/tasks");
    });

    it('transmit a submission to the backend', async () => {

        mockedAxios.post.mockImplementationOnce(() => Promise.resolve(testResult))
        await submitCode(testSubmission)
        expect(mockedAxios.post).toHaveBeenCalled();
        expect(mockedAxios.post).toHaveBeenCalledWith("/api/evaluate", testSubmission);
    })
    it('fetch a user id from backend', async () => {
        mockedAxios.post.mockImplementationOnce(() => Promise.resolve(testUserId));
        let result:string = await fetchNewUser();
        // console.log(result)
        expect(mockedAxios.post).toHaveBeenCalled();
        expect(mockedAxios.post).toHaveBeenCalledWith("/api/user");
        expect(result).toMatch(testUserId);
    })
})