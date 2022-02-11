import {Badge, Container, Tab, Tabs} from "react-bootstrap";
import {useEffect, useState} from "react";
import Editor from "@monaco-editor/react";
import Workspace from "./Workspace";
import axios from "axios";

const b64string = "cHVibGljIGNsYXNzIEluc2VydGlvblNvcnQgewoKICAgIHZvaWQgc29ydChpbnRbXSBhcnIpIHRocm93cyBBcnJheUlzRW1wdHlFeGNlcHRpb24gewogICAgICAgIGludCBuID0gYXJyLmxlbmd0aDsKICAgICAgICBpZihuID09IDApIHRocm93IG5ldyBBcnJheUlzRW1wdHlFeGNlcHRpb24oImVtcHR5IGFycmF5IHByb3ZpZGVkIik7CiAgICAgICAgZm9yIChpbnQgaSA9IDE7IGkgPCBuOyArK2kpIHsKICAgICAgICAgICAgaW50IGtleSA9IGFycltpXTsKICAgICAgICAgICAgaW50IGogPSBpIC0gMTsKCiAgICAgICAgICAgIC8qIE1vdmUgZWxlbWVudHMgb2YgYXJyWzAuLmktMV0sIHRoYXQgYXJlCiAgICAgICAgICAgICAgIGdyZWF0ZXIgdGhhbiBrZXksIHRvIG9uZSBwb3NpdGlvbiBhaGVhZAogICAgICAgICAgICAgICBvZiB0aGVpciBjdXJyZW50IHBvc2l0aW9uICovCiAgICAgICAgICAgIHdoaWxlIChqID49IDAgJiYgYXJyW2pdID4ga2V5KSB7CiAgICAgICAgICAgICAgICBhcnJbaiArIDFdID0gYXJyW2pdOwogICAgICAgICAgICAgICAgaiA9IGogLSAxOwogICAgICAgICAgICB9CiAgICAgICAgICAgIGFycltqICsgMV0gPSBrZXk7CiAgICAgICAgfQogICAgfQp9Cg==";

function TaskTabContent(props) {
    const {name, description, targetDescription, encodedFileString} = props;
    return (
        <div style={{margin: "2rem"}}>
            <h4>
                {name} <Badge bg={"secondary"}>Noch nicht bearbeitet</Badge>
            </h4>
            <p>{description}</p>
            <span>{targetDescription}</span>
            <div style={{padding: "1rem 0"}}>
                <Editor
                    height={"35vh"}
                    defaultLanguage={"java"}
                    theme={"vs-dark"}
                    defaultValue={atob(encodedFileString)}
                    options={{readOnly: true}}
                />
            </div>
            <Workspace/>
        </div>
    )
}


function TaskSelection() {
    const [key, setKey] = useState('1');


    useEffect( () => {
        async function fetchData () {
            await axios.get("/api/tasks").then((res) => {
                console.log(res.data);
            });
        }
        fetchData();
    }, [])

    return (
        <Container style={{paddingTop: "2rem"}}>
            <Tabs
                activeKey={key}
                onSelect={(selectedKey) => {
                    if (selectedKey === key) {
                        setKey(null);
                    } else {
                        setKey(selectedKey);
                    }
                }}
            >
                <Tab eventKey={"1"} title={"Aufgabe 1"}>
                    <TaskTabContent
                        name={"Sortieralgorithmus I: InsertionSort"}
                        description={"Der Sortieralgorithmus InsertionSort in Java implementiert."}
                        target={"Erreichen Sie eine Test-Coverage von 100%."}
                        encodedFileString={b64string}
                    />
                </Tab>
                <Tab eventKey={"2"} title={"Aufgabe 2"}>
                    <TaskTabContent
                        name={"Sortieralgorithmus II: BubbleSort"}
                        description={"Der Sortieralgorithmus BubbleSort in Java implementiert."}
                        target={"Erreichen Sie eine Test-Coverage von 100%."}
                        encodedFileString={b64string}
                    />
                </Tab>
            </Tabs>
        </Container>
    )
}

export default TaskSelection;