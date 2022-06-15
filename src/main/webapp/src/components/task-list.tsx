import React from "react";
import {Task} from "../model/types";
import {ListGroup, Spinner} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {State} from "../redux/reducers";
import {selectTask} from "../redux/actions/selected-task";

function TaskList() {

    const tasks = useSelector((state: State) => state.tasks)
    const progressList = useSelector((state: State) => state.progress.progressList);
    const selectedTask = useSelector((state: State) => state.selectedTask.task)
    const dispatch = useDispatch()

    const onTaskSelect = (task: Task) => {
        dispatch(selectTask(task))
    }


    if (tasks.isLoading) {
        return (
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        )
    }


    return (
        <ListGroup as={"ol"} numbered>
            {tasks.taskList.map((task: Task) => (
                <ListGroup.Item
                    key={task.id}
                    action
                    onClick={() => onTaskSelect(task)}
                    className={"d-flex justify-content-between align-items-start"}
                    disabled={task.id === selectedTask?.id}
                >
                    <div className="ms-2 me-auto w-100">
                        <div className="fw-bold">
                            {task.name}
                        </div>
                        {task.shortDescription}
                        {
                            progressList?.some(progress =>
                                progress.id === task.id
                                && progress.coveredBranches === 100
                                && progress.coveredInstructions === 100
                                && progress.hasAllMutationsPassed
                            ) ? <div className="d-flex flex-row-reverse text-success">
                                    <small>Abgeschlossen</small>
                                </div> :
                                <div className="d-flex flex-row-reverse text-danger">
                                    <small>Nicht abgeschlossen</small>
                                </div>
                        }
                    </div>
                </ListGroup.Item>
            ))}
        </ListGroup>
    )
}

export default TaskList