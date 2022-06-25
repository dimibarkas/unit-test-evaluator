import React, {useState} from "react";
import {Alert, Button, Col, Container, Form,  Row} from "react-bootstrap";
import {requestAuthKey} from "../services";
import {RegistrationCredentials} from "../model/types";

function RegistrationPage() {

    const [formData, updateFormData] = useState<RegistrationCredentials>({
        id: "",
        email: ""
    });

    const [validated, setValidated] = useState(false);
    const [formSubmitted, setFormSubmitted] = useState(false);
    // const [show, setShow] = useState(false);

    // const handleClose = () => setShow(false);
    // const handleShow = () => setShow(true);

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim(),
        })
    }

    // const showPrivacyPolicy = (e) => {
    //     e.preventDefault();
    //     handleShow();
    // }

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
                    <br/>
                    <p> Der Link für das Tool wurde an <b><u> {formData.email}</u></b> geschickt.</p>
                </Alert>
            </Container>
        )
    }

    return (
        <>
            {/*<Modal show={show} onHide={handleClose} centered size={"xl"}>*/}
            {/*    <Modal.Header closeButton>*/}
            {/*        <Modal.Title>Datenschutzerklärung</Modal.Title>*/}
            {/*    </Modal.Header>*/}
            {/*    <Modal.Body>*/}
            {/*        <p>*/}
            {/*            <h4>Einleitung</h4>*/}
            {/*            Die Nutzung dieser Website kann mit der Verarbeitung von personenbezogenen Daten verbunden sein.*/}
            {/*            Damit diese Verarbeitungen für Sie nachvollziehbar sind, möchten wir Ihnen mit den folgenden*/}
            {/*            Informationen einen Überblick zu diesen Verarbeitungen verschaffen.*/}
            {/*            Um eine faire Verarbeitung zu gewährleisten, möchten wir Sie außerdem über Ihre Rechte nach der*/}
            {/*            Europäischen Datenschutz-Grundverordnung (DSGVO) und dem Bundesdatenschutzgesetz (BDSG)*/}
            {/*            informieren.*/}
            {/*        </p>*/}
            {/*        <p>*/}
            {/*            <h4>Kontaktdaten des Verantwortlichen und Datenschutzbeauftragten</h4>*/}
            {/*            Wenn Sie Fragen oder Anregungen zu diesen Informationen haben oder sich wegen der Geltendmachung*/}
            {/*            Ihrer Rechte an uns wenden möchten, richten Sie Ihre Anfrage bitte an <br/> <br/>*/}
            {/*            <div style={{display: "grid", placeItems: "center"}}>*/}
            {/*                <div>*/}
            {/*                    XY GmbH <br/>*/}
            {/*                    Adresse <br/>*/}
            {/*                    E-Mail-Adresse <br/>*/}
            {/*                    Telefonnummer <br/> <br/>*/}
            {/*                </div>*/}
            {/*            </div>*/}
            {/*            Unser Datenschutzbeauftragter ist unter folgenden Kontaktdaten zu erreichen:*/}
            {/*            datenschutzbeauftragter@xygmbh.de*/}
            {/*        </p>*/}
            {/*        <p>*/}
            {/*            <h4>Allgemeine Angaben zur Datenverarbeitung</h4>*/}
            {/*            Wir verarbeiten personenbezogene Daten unter Beachtung der einschlägigen Datenschutzvorschriften, insbesondere der DSGVO und des BDSG.*/}
            {/*            Eine Datenverarbeitung durch uns findet nur auf der Grundlage einer gesetzlichen Erlaubnis statt.*/}
            {/*            Bei der Nutzung dieser Website verarbeiten wir personenbezogene Daten nur mit Ihrer Einwilligung (Art. 6 Abs. 1 Buchst. a) DSGVO), zur Erfüllung eines Vertrags, dessen Vertragspartei Sie sind, oder auf Ihre Anfrage zur Durchführung vorvertraglicher Maßnahmen (Art. 6 Abs. 1 Buchst. b) DSGVO), zur Erfüllung einer rechtlichen Verpflichtung (Art. 6 Abs. 1 Buchst. c) DSGVO) oder wenn die Verarbeitung zu Wahrung unser berechtigten Interessen oder den berechtigten Interessen eines Dritten erforderlich ist, sofern nicht Ihre Interessen oder Grundrechte und Grundfreiheiten, die den Schutz personenbezogener Daten erfordern, überwiegen (Art. 6 Abs. 1 Buchst. f) DSGVO).*/}
            {/*            <h4>Dauer der Speicherung</h4>*/}
            {/*            Sofern sich aus den folgenden Hinweisen nichts anderes ergibt, speichern wir die Daten nur solange, wie es zur Erreichung des Verarbeitungszwecks oder für die Erfüllung unserer vertraglichen oder gesetzlichen Pflichten erforderlich ist. Solche gesetzlichen Aufbewahrungspflichten können sich insbesondere aus handels- oder steuerrechtlichen Vorschriften ergeben.*/}
            {/*        </p>*/}
            {/*        <p>*/}
            {/*            <h4>Datenverarbeitung durch Nutzerangaben</h4>*/}
            {/*            Informationserhebungen durch das Registrierungsformular. <br /> <br />*/}
            {/*            <ul>*/}
            {/*                <li>E-Mail-Adresse</li>*/}
            {/*                <li>Matrikelnummer</li>*/}
            {/*            </ul>*/}
            {/**/}
            {/*            Da es sich um eine Prüfungsleistung handelt ...*/}
            {/*        </p>*/}
            {/*        <p>*/}
            {/*            <h4>Rechte der Betroffenen, Beschwerderecht</h4>*/}
            {/*            <h5>Ihre Rechte</h5>*/}
            {/*            Als betroffene Person haben Sie das Recht, uns gegenüber Ihre Betroffenenrechte geltend zu machen. Dabei haben Sie insbesondere die folgenden Rechte: <br/> <br/>*/}
            {/*            <ul>*/}
            {/*                <li>Sie haben nach Maßgabe des Art.15 DSGVO und § 34 BDSG das Recht, Auskunft darüber zu verlangen, ob und gegebenenfalls in welchen Umfang wir personenbezogene Daten zu Ihrer Person verarbeiten oder nicht.</li>*/}
            {/*                <li>Sie haben das Recht, nach Maßgabe des Art. 16 DSGVO von uns die Berichtigung Ihrer Daten zu verlangen.</li>*/}
            {/*                <li>Sie haben das Recht, nach Maßgabe des Art. 17 DSGVO und § 35 BDSG von uns die Löschung Ihrer personenbezogenen Daten zu verlangen.</li>*/}
            {/*                <li>Sie haben das Recht, nach Maßgabe des Art. 18 DSGVO die Verarbeitung Ihrer personenbezogenen Daten einschränken zu lassen.</li>*/}
            {/*                <li>Sie haben das Recht, nach Maßgabe des Art. 20 DSGVO die Sie betreffenden personenbezogenen Daten, die Sie uns bereitgestellt haben, in einem strukturierten, gängigen und maschinenlesbaren Format zu erhalten, und diese Daten einem anderen Verantwortlichen zu übermitteln.</li>*/}
            {/*                <li>Sie haben nach Maßgabe des Art. 21 Abs. 1 DSGVO das Recht, gegen jede Verarbeitung, die auf der Rechtsgrundlage des Art. 6 Abs. 1 Buchst. e) oder f) DSGVO beruht, Widerspruch einzulegen. Sofern durch uns personenbezogene Daten über Sie zum Zweck der Direktwerbung verarbeitet werden, können Sie gegen diese Verarbeitung gem. Art. 21 Abs. 2 und Abs. 3 DSGVO Widerspruch einlegen.</li>*/}
            {/*            </ul>*/}
            {/*            Sofern Sie uns eine gesonderte Einwilligung in die Datenverarbeitung erteilt haben, können Sie diese Einwilligung nach Maßgabe des Art. 7 Abs. 3 DSGVO jederzeit widerrufen. Durch einen solchen Widerruf wird die Rechtmäßigkeit der Verarbeitung, die bis zum Widerruf aufgrund der Einwilligung erfolgt ist, nicht berührt. <br /> <br />*/}
            {/*            <h4>Beschwerde bei einer Aufsichtsbehörde</h4>*/}
            {/*            Wenn Sie der Ansicht sind, dass eine Verarbeitung der Sie betreffenden personenbezogenen Daten gegen die Bestimmungen der DSGVO verstößt, haben Sie nach Maßgabe des Art. 77 DSGVO das Recht auf Beschwerde bei einer Aufsichtsbehörde.*/}
            {/*        </p>*/}
            {/*    </Modal.Body>*/}
            {/*</Modal>*/}
            <Container className="text-light" style={{position: "relative"}}>
                <h1 className="display-5 my-3">Registrierung</h1>
                {/*<p className="mt-5">*/}
                {/*    Bitte geben Sie Ihre E-Mail-Adresse und Matrikelnummer ein und bestätigen Sie die{" "}*/}
                {/*    <a href={""} onClick={showPrivacyPolicy}>Datenschutzerklärung</a>.<br/>*/}
                {/*    Sie erhalten dann per E-Mail einen Link, mit dem Sie das Tool aufrufen können.*/}
                {/*</p>                */}
                <p className="mt-5">
                    Bitte geben Sie Ihre E-Mail-Adresse und Matrikelnummer ein.<br/>
                    Sie erhalten dann per E-Mail einen Link, mit dem Sie das Tool aufrufen können.
                </p>

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
                    {/*<Row>*/}
                    {/*    <Form.Group as={Col} className="mb-3">*/}
                    {/*        <Form.Check*/}
                    {/*            required*/}
                    {/*            label={"Ich stimme der Datenschutzerklärung zu."}*/}
                    {/*            feedback="Um dich zu registrieren musst du der Datenschutzerklärung zustimmen."*/}
                    {/*            feedbackType="invalid"*/}
                    {/*        />*/}
                    {/*    </Form.Group>*/}
                    {/*</Row>*/}
                    <Row>
                        <Col>
                            <Button type="submit">
                                Senden
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </Container>
        </>
    )
}

export default RegistrationPage;