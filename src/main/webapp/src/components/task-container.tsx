import {Alert, Badge, Button, Container, ProgressBar, Tab, Tabs, Toast, ToastContainer} from "react-bootstrap";
import Editor from "@monaco-editor/react";
import {useDispatch, useSelector} from "react-redux";
import {State} from "../redux/reducers";
import React, {useEffect, useRef, useState} from "react";
import TaskList from "./task-list";
import {Submission, SubmissionResult} from "../model/types";
import {submitCode} from "../services";
import useAlert from "../hooks/use-alert";
import Split from "react-split";
import {BsPlayFill} from "react-icons/bs";
import {fetchProgressList} from "../redux/actions/progress";


function TaskContainer() {

    const selectedTask = useSelector((state: State) => state.selectedTask);
    const user = useSelector((state: State) => state.user);
    const progress = useSelector((state: State) => state.progress);
    const {showAlert, setShowAlert, showCustomAlert, header, variant, output, showVideoPlayer, videoTitle} = useAlert();
    const [key, setKey] = useState(null)
    const [isLoading, setLoading] = useState(false);
    const [ciProgress, setCiProgress] = useState(0);
    const [cbProgress, setCbProgress] = useState(0);
    const [showToast, setShowToast] = useState(true);
    const dispatch = useDispatch()
    const editorRef = useRef(null);

    const toggleShowToast = () => setShowToast(false);

    function submitButton() {
        return (
            <div className="d-flex align-items-center">
                <BsPlayFill/>
                <div className="mx-2">Ausführen</div>
            </div>
        )
    }

    function getProgressForSelectedTask(): void {
        progress.progressList?.filter((progress) => progress.id === selectedTask.task.id).forEach(progress => {
            setCiProgress(progress.coveredInstructions);
            setCbProgress(progress.coveredBranches);
        })
    }

    function getVariant(progress: number): string {
        if (progress <= 20) {
            return "danger"
        } else if (progress > 20 && progress < 80) {
            return "warning"
        } else if (progress >= 80 && progress < 99) {
            return "info"
        } else if (progress === 100) {
            return "success"
        }
    }

    function handleEditorDidMount(editor) {
        editorRef.current = editor;
    }

    

    useEffect(() => {
        if (!progress.isLoading) {
            getProgressForSelectedTask();
        }
        // eslint-disable-next-line
    }, [progress.isLoading, selectedTask])

    useEffect(() => {
        if (isLoading) {
            setShowAlert(false);
            const request: Submission = {
                taskId: selectedTask.task.id,
                encodedTestContent: btoa(editorRef.current.getValue()),
                userId: user.user.id
            }
            submitCode(request).then((receivedTest: SubmissionResult) => {
                showCustomAlert(receivedTest)
                dispatch(fetchProgressList(user.user.id));
                setLoading(false);
            }).catch((error) => {
                setLoading(false);
                console.log(error)
            });
        } else if (!isLoading) {
            if (user?.user?.id) {
                fetchProgressList(user.user.id);
            }
        }
        // eslint-disable-next-line
    }, [isLoading]);

    const handleClick = () => setLoading(true);

    const isCurrentTaskCompleted = (): boolean => {
        return progress.progressList?.some(progress =>
            progress.id === selectedTask.task.id
            && progress.coveredBranches === 100
            && progress.coveredInstructions === 100
            && progress.hasAllMutationsPassed)
    }

    /**
     * if no task is selected
     */
    if (selectedTask.task === null) {
        return (
            <>
                <Container>
                    <p className="lead text-light my-4">Bitte Aufgabe wählen.</p>
                    <TaskList/>
                </Container>
            </>
        )
    }


    return (
        <>
            <Container className="text-light" style={{position: "relative"}}>
                <ToastContainer position={"top-end"} style={{zIndex: 1000}}>
                    <Toast show={showToast} onClose={toggleShowToast}>
                        <Toast.Header>
                            <strong className="me-auto">Neues Feedback!</strong>
                        </Toast.Header>
                        <Toast.Body>
                            <video src={`/api/video/${videoTitle}`} width="320" height="200" controls
                                   preload="none"/>
                        </Toast.Body>
                    </Toast>
                </ToastContainer>
                <div style={{width: "70%", height: 300}}>
                    <h1 className="display-5 my-2">{selectedTask.task.name}</h1>
                    <small className={isCurrentTaskCompleted() ? "text-success" : "text-danger"}>
                        {isCurrentTaskCompleted() ? "Abgeschlossen" : "Nicht abgeschlossen"}
                    </small>
                    {/*<p className="lead my-2">{selectedTask.task.shortDescription}</p>*/}
                    <p className="lead my-2"><u>Ziel:</u> {selectedTask.task.targetDescription}</p>
                </div>
                <div className="d-flex flex-row-reverse justify-content-between align-items-center mb-3">
                    <Button
                        variant={isLoading ? "secondary" : "success"}
                        disabled={isLoading}
                        onClick={!isLoading ? handleClick : null}
                    >
                        {isLoading ? 'Verarbeitung läuft…' : submitButton()}
                    </Button>
                    <div className="w-75 d-flex justify-content-between align-items-center flex-shrink-1">
                        <small className="text-nowrap">
                            covered instructions
                        </small>
                        <ProgressBar variant={getVariant(ciProgress)} now={ciProgress} label={`${ciProgress} %`}
                                     className="w-100 m-2 text-black"/>
                        <small className="text-nowrap">
                            covered branches
                        </small>
                        <ProgressBar variant={getVariant(cbProgress)} now={cbProgress} label={`${cbProgress} %`}
                                     className="w-100 m-2 text-black"/>
                    </div>
                </div>
                <Split
                    className="split"
                    style={{height: "100%"}}
                    sizes={[50, 50]}
                    minSize={100}
                    expandToMin={false}
                    gutterSize={10}
                    gutterAlign="center"
                    snapOffset={30}
                    dragInterval={1}
                    direction="horizontal"
                    cursor="col-resize"
                >
                    <Editor
                        height={"40vh"}
                        width={"100%"}
                        defaultLanguage={"java"}
                        theme={"vs-dark"}
                        options={{readOnly: true}}
                        value={atob(selectedTask.task.encodedFile)}
                    />
                    <Editor
                        height={"40vh"}
                        width={"100%"}
                        defaultLanguage={"java"}
                        theme={"vs-dark"}
                        onMount={handleEditorDidMount}
                        value={atob(selectedTask.task.encodedTestTemplate)}
                    />
                </Split>
                <div className="my-4">
                    <Alert show={showAlert} variant={variant} onClose={() => setShowAlert(false)} dismissible>
                        <Alert.Heading>{header}</Alert.Heading>
                        <hr/>

                        <Tabs activeKey={key} onSelect={((k) => {
                            if (k === key) {
                                setKey("")
                            } else {
                                setKey(k);
                            }
                        })}>
                            <Tab eventKey="console" title="Konsolenausgabe anzeigen">
                                <pre className="bg-white p-2">
                                {output}
                                </pre>
                            </Tab>
                        </Tabs>
                    </Alert>
                </div>
            </Container>
        </>
    )
}

export default TaskContainer;