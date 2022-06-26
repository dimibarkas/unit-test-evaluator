import React from "react";
import {Alert, Container, Table} from "react-bootstrap";
import {useSelector} from "react-redux";
import {State} from "../redux/reducers";
import {Task} from "../model/types";

function FinalPage() {

    const tasks = useSelector((state: State) => state.tasks)

    const formatSeconds = (task: Task) => {
        const timeInSeconds = Number(sessionStorage.getItem(`T${task.id}`)) ? Number(sessionStorage.getItem(`T${task.id}`)) : 0;
        console.log(timeInSeconds)
        return new Date(timeInSeconds * 1000).toISOString().slice(14, 19)
    }

    return (
        <>
            <Container className={"my-5"}>
                <Alert variant={"info"}>
                    <Alert.Heading>
                        Alle Aufgaben abgeschlossen!
                    </Alert.Heading>
                    <p>
                        Herzlichen Glückwunsch. Du hast alle Aufgaben erfolgreich abgeschlossen.
                    </p>
                    <Table striped>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Name der Aufgabe</th>
                            <th>Benötigte Zeit</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tasks.taskList.map((task) => (
                            <tr key={task.id}>
                                <td>{task.id}</td>
                                <td>{task.name}</td>
                                <td>{formatSeconds(task)}</td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                    <br />
                    Bitte fahre nun mit folgender <Alert.Link href={"https://www.umfrageonline.com/c/cukc4fsz"}>Umfrage</Alert.Link> fort.
                </Alert>
            </Container>
        </>
    )
}

export default FinalPage;