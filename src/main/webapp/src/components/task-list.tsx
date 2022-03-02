import React from "react";
import {Task} from "../model/types";
import {ListGroup, Spinner} from "react-bootstrap";
import {useSelector} from "react-redux";
import {State} from "../redux/reducers";

function TaskList() {
    const tasks = useSelector((state: State) => state.tasks)

    if(tasks.isLoading) return (
        <Spinner animation="border" role="status">
            <span className="visually-hidden">Loading...</span>
        </Spinner>
    )

    return (
        <ListGroup as={"ol"} numbered>
            {tasks.taskList.map((task: Task) => (
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