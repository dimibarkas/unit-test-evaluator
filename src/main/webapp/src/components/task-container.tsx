import {Alert, Col, Container, Row} from "react-bootstrap";
import Editor from "@monaco-editor/react";
import Workspace from "./workspace";

function TaskContainer() {

    return (
        <>
            <Container fluid>
                <Container style={{color: "white"}}>
                    <h2>h1. Bootstrap heading <small>Secondary text</small></h2>
                </Container>
                <Row>
                    <Col xs={6} xl={6}>
                        <Editor
                            height={"45vh"}
                            defaultLanguage={"java"}
                            theme={"vs-dark"}
                            options={{readOnly: true}}
                        />
                    </Col>
                    <Col xs={6} xl={6}>
                        <Workspace/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Alert variant="danger" dismissible>
                            <Alert.Heading>Oh snap! You got an error!</Alert.Heading>
                            <p>
                                Change this and that and try again. Duis mollis, est non commodo
                                luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit.
                                Cras mattis consectetur purus sit amet fermentum.
                            </p>
                        </Alert>
                    </Col>
                </Row>
            </Container>
        </>
    )
}

export default TaskContainer;