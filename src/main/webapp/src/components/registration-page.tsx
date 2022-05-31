import React, {useState} from "react";
import {Alert, Button, Col, Container, Form, Row} from "react-bootstrap";
import {requestAuthKey} from "../services";
import {RegistrationCredentials} from "../model/types";

function RegistrationPage() {

    const [formData, updateFormData] = useState<RegistrationCredentials>({
        id: "",
        email: ""
    });

    const [validated, setValidated] = useState(false);
    const [formSubmitted, setFormSubmitted] = useState(false);

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim(),
        })
    }

    const handleSubmit = (e) => {
        const form = e.currentTarget;
        if (form.checkValidity() === false) {
            e.preventDefault();
            e.stopPropagation();
        } else {
            e.preventDefault();
            e.stopPropagation();
            requestAuthKey(formData).then(
                () => {
                    setFormSubmitted(true);
                    console.log("authentication mail requested")
                })
        }

        setValidated(true);
    }

    if (formSubmitted) {
        return (
            <Container className={"my-5"}>
                <Alert variant={"success"}>
                    <h4>Vielen Dank!</h4>
                    <br />
                    <p> Der Link für das Tool wurde an <b><u> {formData.email} </u></b> geschickt.</p>
                </Alert>
            </Container>
        )
    }

    return (
        <Container className="text-light" style={{position: "relative"}}>
            <h1 className="display-5 my-3">Registrierung</h1>
            <p>Bitte geben Sie Ihre E-Mail-Adresse und Matrikelnummer ein.<br/> <br/>Sie erhalten dann per E-Mail einen
                Link, mit dem Sie das Tool aufrufen können.</p>

            <Form noValidate validated={validated} onSubmit={handleSubmit} className="my-5">
                <Row>
                    <Form.Group as={Col} md={4}>
                        <Form.Label>Matrikelnummer</Form.Label>
                        <Form.Control
                            required
                            value={formData.id}
                            name="id"
                            className="text-light"
                            type="text"
                            placeholder="Bitte Matrikelnummer eingeben"
                            onChange={handleChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Das Feld Matrikelnummer ist ein Pflichtfeld.
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group as={Col} className="mb-3">
                        <Form.Label>E-Mail-Adresse</Form.Label>
                        <Form.Control
                            required
                            value={formData.email}
                            name="email"
                            className="text-light"
                            type="email"
                            placeholder="Bitte Mail eingeben"
                            onChange={handleChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Das Feld E-Mail-Adresse ist ein Pflichtfeld.
                        </Form.Control.Feedback>
                    </Form.Group>
                </Row>
                <Row>
                    <Col>
                        <Button type="submit">
                            Senden
                        </Button>
                    </Col>
                </Row>
            </Form>
        </Container>
    )
}

export default RegistrationPage;