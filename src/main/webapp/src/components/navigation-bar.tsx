import {Container, Nav, Navbar, Offcanvas} from "react-bootstrap";
import {useState} from "react";
import {MdDashboard} from "react-icons/md"
import React from "react";
import TaskList from "./task-list";


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
                    <TaskList />
                </Offcanvas.Body>
            </Offcanvas>
        </>
    )
}



export default NavigationBar;