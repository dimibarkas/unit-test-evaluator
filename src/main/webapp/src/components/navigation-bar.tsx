import {Container, ListGroup, Nav, Navbar, Offcanvas, Spinner} from "react-bootstrap";
import {useEffect, useState} from "react";
import {getAllTasks} from "../services/tasks";
import {Task} from "../types/Task";
import {MdDashboard} from "react-icons/md"
import React from "react";


function NavigationBar() {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <Navbar style={{backgroundColor: "#39393a"}}>
                <Container>
                    <Navbar.Brand style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <svg width={20} height={20}>
                            <polygon points={"10,20  10,0 20,10"}
                                     style={{fill: "white"}}/>
                        </svg>
                        {' '}
                        <div style={{paddingLeft: ".4rem", color: "white"}}>
                            Unit-Test Evaluator
                        </div>
                    </Navbar.Brand>
                    <Nav>
                        <div style={{color: "white", cursor: "pointer"}} onClick={handleShow} >
                            <h3>
                                <MdDashboard />
                            </h3>
                        </div>
                    </Nav>
                </Container>
            </Navbar>
            <Offcanvas show={show} onHide={handleClose} placement={"end"}>
                <Offcanvas.Header closeButton>
                    <Offcanvas.Title>Menü</Offcanvas.Title>
                </Offcanvas.Header>
                <Offcanvas.Body>
                    <p>
                        Alle Aufgaben
                    </p>
                    <TaskList/>
                </Offcanvas.Body>
            </Offcanvas>
        </>
    )
}

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

export default NavigationBar;