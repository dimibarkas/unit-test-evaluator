import React, {useState} from "react";
import {Alert, Button, Col, Container, Form, Row} from "react-bootstrap";
import {requestAuthKey} from "../services";
import {RegistrationCredentials} from "../model/types";
import {object, string} from 'yup';
import {Formik} from "formik";


function RegistrationPage() {

    const schema = object().shape({
        id: string()
            .required('Das Feld Matrikelnummer ist ein Pflichtfeld.')
            .length(8, 'Bitte geben Sie Ihre 8-stellige Matrikelnummer ein.'),
        email: string()
            .required('Das Feld E-Mail-Adresse ist ein Pflichtfeld.')
            .matches(new RegExp('(?:[a-z]\\w+).(?:[a-z]\\w+)@(stud.hs-ruhrwest.de|hs-ruhrwest.de)'),
                'Bitte geben Sie Ihre Mail-Adresse der Hochschule in der Form vorname.nachname@stud.hs-ruhrwest.de.')
    })
    const [formSubmitted, setFormSubmitted] = useState(false);
    const [error, setError] = useState(false);
    const [formData, setFormData] = useState<RegistrationCredentials>();


    const handleSubmit = (values) => {
        setFormData(values);
        requestAuthKey(values).then(
            () => {
                setFormSubmitted(true);
                console.log("authentication mail requested")
            })
            .catch((e) => {
                if (e === "an error occurred while requesting authentication mail.") {
                    setError(true);
                }
                console.log(e);
            })
    }

    if (error) {
        return (
            <Container className={"my-5"}>
                <Alert variant={"danger"}>
                    <h4>Mail konnte nicht gesendet werden.</h4>
                    <br/>
                    <p>Beim Versenden der Mail ist leider ein Fehler aufgetreten, bitte laden Sie die Seite neu und
                        versuchen Sie es erneut.</p>
                </Alert>
            </Container>
        )
    }

    if (formSubmitted) {
        return (
            <Container className={"my-5"}>
                <Alert variant={"success"}>
                    <h4>Vielen Dank!</h4>
                    <br/>
                    <p> Der Link für das Tool wurde an <b><u> {formData.email}</u></b> geschickt.</p>
                </Alert>
            </Container>
        )
    }

    return (
        <>
            <Container className="text-light" style={{position: "relative"}}>
                <h1 className="display-5 my-3">Registrierung</h1>
                <p className="mt-5">
                    Bitte geben Sie Ihre E-Mail-Adresse und Matrikelnummer ein.<br/>
                    Sie erhalten dann per E-Mail einen Link, mit dem Sie das Tool aufrufen können.
                </p>

                <Formik
                    validationSchema={schema}
                    initialValues={{
                        id: '',
                        email: '',
                    }}
                    onSubmit={values => {
                        handleSubmit(values)
                    }}
                >
                    {({
                          errors,
                          touched,
                          values,
                          handleChange,
                          handleSubmit,
                      }) => (
                        <Form
                            noValidate
                            onSubmit={handleSubmit}
                            className="my-5">
                            <Row>
                                <Form.Group as={Col} md={4}>
                                    <Form.Label>Matrikelnummer</Form.Label>
                                    <Form.Control
                                        required
                                        value={values.id}
                                        name="id"
                                        className="text-light"
                                        type="text"
                                        placeholder="Bitte Matrikelnummer eingeben"
                                        onChange={handleChange}
                                        isValid={touched.id && !errors.id}
                                        isInvalid={!!errors.id && touched.id}
                                    />
                                    <Form.Control.Feedback type={"invalid"}>
                                        {errors.id}
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <Form.Group as={Col} className="mb-3">
                                    <Form.Label>E-Mail-Adresse</Form.Label>
                                    <Form.Control
                                        required
                                        value={values.email}
                                        name="email"
                                        className="text-light"
                                        type="email"
                                        placeholder="Bitte Mail eingeben"
                                        onChange={handleChange}
                                        isValid={touched.email && !errors.email}
                                        isInvalid={!!errors.email && touched.email}
                                    />
                                    <Form.Control.Feedback type={"invalid"}>
                                        {errors.email}
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
                    )}
                </Formik>

            </Container>
        </>
    )
}

export default RegistrationPage;