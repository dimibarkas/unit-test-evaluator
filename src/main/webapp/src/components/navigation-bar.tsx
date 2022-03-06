import {Container, Nav, Navbar, Offcanvas} from "react-bootstrap";
import {useEffect, useState} from "react";
import React from "react";
import {BiMenuAltRight} from "react-icons/bi"
import TaskList from "./task-list";
import {store} from "../redux/store";
import {State} from "../redux/reducers";
import {Task} from "../model/types";


function NavigationBar() {
    const [show, setShow] = useState(false);

    function select(state:State) {
        return state.selectedTask?.task?.id
    }

    // state subscriber
    let currentValue
    function handleChange() {
        let previousValue = currentValue
        currentValue = select(store.getState())
        if (previousValue !== currentValue) handleClose()
    }

    useEffect(() => {
        store.subscribe(handleChange)
        // eslint-disable-next-line
    }, [])

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
                        <button className="text-light btn" onClick={handleShow}>
                            <h3>
                                <BiMenuAltRight />
                            </h3>
                        </button>
                    </Nav>
                </Container>
            </Navbar>
            <Offcanvas show={show} onHide={handleClose} placement={"end"}>
                <Offcanvas.Header closeButton>
                    <Offcanvas.Title>Menü</Offcanvas.Title>
                </Offcanvas.Header>
                <Offcanvas.Body>
                    <p className="lead">
                        Alle Aufgaben
                    </p>
                    <TaskList/>
                </Offcanvas.Body>
            </Offcanvas>
        </>
    )
}


export default NavigationBar;