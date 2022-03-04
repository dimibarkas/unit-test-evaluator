import {Alert, Button, Container} from "react-bootstrap";
import Editor from "@monaco-editor/react";
import {useSelector} from "react-redux";
import {State} from "../redux/reducers";
import React, {useEffect, useRef, useState} from "react";
import TaskList from "./task-list";
import {EvaluationRequest, TestResult} from "../model/types";
import {submitCode} from "../services/tasks";
import useAlert from "../hooks/use-alert";
import Split from "react-split";


function TaskContainer() {

    const selectedTask = useSelector((state: State) => state.selectedTask);
    const {showAlert, setShowAlert, showCustomAlert, header, variant} = useAlert();

    const [isLoading, setLoading] = useState(false);
    const editorRef = useRef(null);


    function handleEditorDidMount(editor) {
        editorRef.current = editor;
    }

    useEffect(() => {
        if (isLoading) {
            setShowAlert(false);
            const request: EvaluationRequest = {
                taskId: selectedTask.task.id,
                encodedTestContent: btoa(editorRef.current.getValue())
            }
            submitCode(request).then((receivedTest: TestResult) => {
                showCustomAlert(receivedTest)
                setLoading(false);
            }).catch((error) => {
                console.log(error)
            });
        }
    }, [isLoading]);

    const handleClick = () => setLoading(true);

    /**
     * if no task is selected
     */
    if (selectedTask.task === null) {
        return (
            <>
                <Container>
                    <h1 className="text-light display-6 my-4">Bitte eine Aufgabe zum bearbeiten wählen.</h1>
                    <TaskList/>
                </Container>
            </>
        )
    }

    return (
        <>
            <Container className="text-light">
                <h1 className="display-5 my-4">{selectedTask.task.name}</h1>
                <p className="lead my-2">{selectedTask.task.description}</p>
                <p className="lead my-2"><u>Ziel:</u> {selectedTask.task.targetDescription}</p>
                <div className="d-flex flex-row-reverse mb-3">
                    <Button
                        variant="primary"
                        disabled={isLoading}
                        onClick={!isLoading ? handleClick : null}
                    >
                        {isLoading ? 'Verarbeitung läuft…' : 'Ausführen'}
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
                        <p>
                            Kurzbeschreibung des Fehlers und die Möglichkeit ein Video abzuspielen.
                        </p>
                    </Alert>
                </div>
            </Container>

        </>
    )
}

export default TaskContainer;