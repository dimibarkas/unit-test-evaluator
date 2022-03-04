import {Alert, Button, Col, Container, Row} from "react-bootstrap";
import Editor from "@monaco-editor/react";
import {useSelector} from "react-redux";
import {State} from "../redux/reducers";
import React, {useEffect, useRef, useState} from "react";
import TaskList from "./task-list";
import {EvaluationRequest, TestResult} from "../model/types";
import {submitCode} from "../services/tasks";
import useAlert from "../hooks/use-alert";


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
            submitCode(request).then((receivedTest:TestResult) => {
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
            <Container fluid>
                <Container className="text-light">
                    <h1 className="display-6 my-4">{selectedTask.task.name}</h1>
                    <h5>{selectedTask.task.description}</h5>
                    <p><u>Ziel:</u> {selectedTask.task.targetDescription}</p>
                </Container>
                <Row>
                    <Col xs={6} xl={6}>
                        <Editor
                            height={"45vh"}
                            defaultLanguage={"java"}
                            theme={"vs-dark"}
                            options={{readOnly: true}}
                            value={atob(selectedTask.task.encodedFile)}
                        />
                    </Col>
                    <Col xs={6} xl={6}>
                        <Editor
                            height={"45vh"}
                            defaultLanguage={"java"}
                            theme={"vs-dark"}
                            onMount={handleEditorDidMount}
                            value={atob(selectedTask.task.encodedTestTemplate)}
                        />
                    </Col>
                </Row>
                <Container className="my-4">
                    <Alert show={showAlert} variant={variant} onClose={() => setShowAlert(false)} dismissible>
                        <Alert.Heading>{header}</Alert.Heading>
                        <p>
                            Change this and that and try again. Duis mollis, est non commodo
                            luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit.
                            Cras mattis consectetur purus sit amet fermentum.
                        </p>
                    </Alert>
                </Container>
            </Container>
            <Container className="d-flex flex-row-reverse mt-3">
                <Button
                    variant="primary"
                    disabled={isLoading}
                    onClick={!isLoading ? handleClick : null}
                >
                    {isLoading ? 'Verarbeitung läuft…' : 'Ausführen'}
                </Button>
            </Container>
        </>
    )
}

export default TaskContainer;