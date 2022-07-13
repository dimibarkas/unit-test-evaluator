import React from "react";
import {Alert, Button, Container, Table} from "react-bootstrap";
import {useSelector} from "react-redux";
import {State} from "../redux/reducers";
import {Task} from "../model/types";

function FinalPage() {

    const tasks = useSelector((state: State) => state.tasks);
    const progress = useSelector((state: State) => state.progress);

    const countCompletedTasks = (): number => {
        let numTasksPassed = 0;
        progress?.progressList?.forEach((progress) => {
            if (progress.coveredBranches === 100 &&
                progress.coveredInstructions === 100 &&
                progress.hasAllMutationsPassed) {
                numTasksPassed++;
            }
        })
        return numTasksPassed;
    }

    const formatSeconds = (task: Task) => {
        const timeInSeconds = Number(sessionStorage.getItem(`T${task.id}`)) ? Number(sessionStorage.getItem(`T${task.id}`)) : 0;
        return new Date(timeInSeconds * 1000).toISOString().slice(14, 19)
    }

    return (
        <>
            <Container className={"my-5"}>
                <Alert variant={"dark"}>
                    <Alert.Heading>
                        Tool abgeschlossen!
                    </Alert.Heading>
                    <div className={"d-flex align-items-center justify-content-center flex-column"}>
                        <p>Herzlichen Glückwunsch.</p>
                        <p>Du hast {countCompletedTasks()} von 6 Aufgaben erfolgreich abgeschlossen.</p>
                    </div>
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
                    <br/>
                    <div className={"d-grid"}>
                        <Button href={"https://www.umfrageonline.com/c/cukc4fsz"} target={"_blank"}>
                            Weiter zur letzten Umfrage
                        </Button>
                    </div>
                </Alert>
            </Container>
        </>
    )
}

export default FinalPage;