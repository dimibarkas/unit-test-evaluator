import {
    Button,
    Container,
    Nav,
    Navbar,
    Offcanvas,
    OverlayTrigger,
    Popover,
} from "react-bootstrap";
import {useEffect, useState} from "react";
import React from "react";
import {BiMenuAltRight} from "react-icons/bi"
import TaskList from "./task-list";
import {store} from "../redux/store";
import {State} from "../redux/reducers";
import {useDispatch, useSelector} from "react-redux";
import {studentHasQuit} from "../redux/actions/progress";

function NavigationBar() {
    const [show, setShow] = useState(false);

    const selectedTask = useSelector((state: State) => state.selectedTask);
    const progress = useSelector((state: State) => state.progress)
    const dispatch = useDispatch();

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

    const handleQuitButton = () => {
        dispatch(studentHasQuit())
        handleClose()
    }


    /**
     * helper method
     * @param state
     */
    function select(state: State) {
        return state.selectedTask?.task?.id
    }

    let currentValue

    /**
     * subscriber method
     */
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
                    <Navbar.Brand className="d-flex flex-row align-items-center">
                        <svg width={20} height={20}>
                            <polygon points={"10,20  10,0 20,10"}
                                     style={{fill: "white"}}/>
                        </svg>
                        {' '}
                        <div className="text-light mx-2">
                            Unit-Test-Evaluator
                        </div>
                    </Navbar.Brand>
                    <Nav style={{display: selectedTask.task == null || progress.hasQuit ? "none" : "inherit"}}>
                        <button className="text-light btn" onClick={handleShow}>
                            <h3>
                                <BiMenuAltRight/>
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
                    <div className={"d-flex align-items-center justify-content-center py-5"}>
                        <OverlayTrigger
                            flip={false}
                            delay={{show: 100, hide: 400}}
                            defaultShow={false}
                            trigger={["focus", "hover"]}
                            placement={"top"}
                            overlay={(
                                <Popover>
                                    <Popover.Header>
                                        Tool beenden
                                    </Popover.Header>
                                    <Popover.Body>
                                        Mindestens 3 Aufgaben müssen abgeschlossen sein.<br/>
                                        <br/>
                                        Sie haben{' '}
                                        <span
                                            className={countCompletedTasks() < 3 ? "text-danger" : "text-success"}
                                        >
                                            {countCompletedTasks()} / 3 </span>abgeschlossen.
                                    </Popover.Body>
                                </Popover>
                            )}
                        >
                            <div>
                                <Button disabled={!progress.hasMinTasksPassed} onClick={handleQuitButton}>
                                    Abschließen
                                </Button>
                            </div>
                        </OverlayTrigger>

                    </div>

                </Offcanvas.Body>
            </Offcanvas>
        </>
    )
}


export default NavigationBar;