import {Alert, Button, Container, Tab, Tabs} from "react-bootstrap";
import Editor from "@monaco-editor/react";
import {useSelector} from "react-redux";
import {State} from "../redux/reducers";
import React, {useEffect, useRef, useState} from "react";
import TaskList from "./task-list";
import {Submission, SubmissionResult} from "../model/types";
import {submitCode} from "../services";
import useAlert from "../hooks/use-alert";
import Split from "react-split";
import {BsPlayFill} from "react-icons/bs";


function TaskContainer() {

    const selectedTask = useSelector((state: State) => state.selectedTask);
    const user = useSelector((state: State) => state.user);
    const {showAlert, setShowAlert, showCustomAlert, header, variant, output, showVideoPlayer, videoTitle} = useAlert();
    const [key, setKey] = useState(null)
    const [isLoading, setLoading] = useState(false);
    const editorRef = useRef(null);

    function submitButton() {
        return (
            <div className="d-flex align-items-center">
                <BsPlayFill/>
                <div className="mx-2">Ausführen</div>
            </div>
        )
    }

    function handleEditorDidMount(editor) {
        editorRef.current = editor;
    }


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
                setLoading(false);
            }).catch((error) => {
                setLoading(false);
                console.log(error)
            });
        }
        // eslint-disable-next-line
    }, [isLoading]);

    const handleClick = () => setLoading(true);

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
            <Container className="text-light">
                <h1 className="display-5 my-4">{selectedTask.task.name}</h1>
                <p className="lead my-2">{selectedTask.task.shortDescription}</p>
                <p className="lead my-2"><u>Ziel:</u> {selectedTask.task.targetDescription}</p>
                <div className="d-flex flex-row-reverse mb-3">
                    <Button
                        variant={isLoading ? "secondary" : "success"}
                        disabled={isLoading}
                        onClick={!isLoading ? handleClick : null}
                    >
                        {isLoading ? 'Verarbeitung läuft…' : submitButton()}
                    </Button>
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
                        height={"45vh"}
                        width={"100%"}
                        defaultLanguage={"java"}
                        theme={"vs-dark"}
                        options={{readOnly: true}}
                        value={atob(selectedTask.task.encodedFile)}
                    />
                    <Editor
                        height={"45vh"}
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
                        <div
                            style={{
                                display: showVideoPlayer ? "flex" : "none",
                                alignItems: "center",
                                justifyContent: "center",
                                width: "100%"
                            }}>
                            <video src={`/api/video/${videoTitle}`} width="80%" height="80%" controls
                                   preload="none"/>
                        </div>
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