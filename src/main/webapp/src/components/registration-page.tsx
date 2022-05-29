import React, {useState} from "react";
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {requestAuthKey} from "../services";
import {RegistrationCredentials} from "../model/types";

function RegistrationPage() {

    const [formData, updateFormData] = useState<RegistrationCredentials>({
        id: "",
        email: ""
    });

    const [validated, setValidated] = useState(false);

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
            requestAuthKey(formData).then(r => console.log("authentication mail requested"))
        }

        setValidated(true);
    }

    return (
        <Container className="text-light" style={{position: "relative"}}>
            <h1 className="display-5 my-3">Anmeldung</h1>
            <p>Bitte geben Sie unten Ihren Vornamen, Nachnamen und Ihre E-Mail-Adresse und Matrikelnummer ein. Sie
                erhalten dann per E-Mail einen Link, mit dem Sie die Einsicht aufrufen können.</p>

            <Form noValidate validated={validated} onSubmit={handleSubmit}>
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
                        <Button type="submit" variant="success">
                            Anmelden
                        </Button>
                    </Col>
                </Row>
            </Form>
        </Container>
    )
}

export default RegistrationPage;