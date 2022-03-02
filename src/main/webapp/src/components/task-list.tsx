import React, {useEffect, useState} from "react";
import {Task} from "../types/Task";
import {getAllTasks} from "../services/tasks";
import {ListGroup, Spinner} from "react-bootstrap";

function TaskList() {
    const [tasks, setTasks] = useState<Task[] | undefined>(undefined);
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        getAllTasks().then((returnedTasks) => {
            setTasks(returnedTasks);
            setLoading(false);
        }).catch((error) => {
            console.log(error);
        })
    }, [])


    if(loading) return (
        <Spinner animation="border" role="status">
            <span className="visually-hidden">Loading...</span>
        </Spinner>
    )

    return (
        <ListGroup as={"ol"} numbered>
            {tasks.map((task: Task) => (
                <ListGroup.Item key={task.id} action as={"li"} className={"d-flex justify-content-between align-items-start"}>
                    <div className="ms-2 me-auto">
                        <div className="fw-bold">{task.name}</div>
                        {task.description}
                    </div>
                </ListGroup.Item>
            ))}
        </ListGroup>
    )
}

export default TaskList