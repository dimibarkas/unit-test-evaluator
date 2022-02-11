import {Container, Tab, Tabs} from "react-bootstrap";
import {useState} from "react";

function TaskTabContent(props) {
    const {name, description, target} = props;
    return (
        <div style={{margin: "2rem"}}>
            <h4>{name}</h4>
            <p>{description}</p>
            <span>{target}</span>
            
        </div>
    )
}


function TaskSelection() {

    const [key, setKey] = useState('1');
    return (
        <Container style={{paddingTop: "2rem"}}>
            <Tabs
                activeKey={key}
                onSelect={(key) => setKey(key)}
            >
                <Tab eventKey={"1"} title={"Aufgabe 1"}>
                    <TaskTabContent
                        name={"Sortieralgorithmus I: InsertionSort"}
                        description={"Der Sortieralgorithmus InsertionSort in Java implementiert."}
                        target={"Erreichen Sie eine Test-Coverage von 100%."}
                    />
                </Tab>
                <Tab eventKey={"2"} title={"Aufgabe 2"}>
                    <TaskTabContent/>
                </Tab>
            </Tabs>
        </Container>
    )
}

export default TaskSelection;