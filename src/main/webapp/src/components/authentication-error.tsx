import React from "react";
import {Alert, Container} from "react-bootstrap";

function AuthenticationError() {
    return (
        <Container>
            <Alert className="my-5" variant="danger">
                <Alert.Heading>Authentifizierung fehlgeschlagen</Alert.Heading>
                <p>
                    Der Authentifizierungsschlüssel scheint nicht zu der Matrikelnummer zu passen oder ist fehlerhaft. <br/> <br/>

                    Falls du das Registrierungsformular erneut ausgefüllt hast, nutze bitte den neuen Link um auf das Tool geleitet zu werden.
                </p>
            </Alert>
        </Container>
    )
}


export default AuthenticationError;