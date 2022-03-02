import Editor from "@monaco-editor/react";
import {useEffect, useRef, useState} from "react";
import {Button} from "react-bootstrap";
import axios from "axios";

const b64string = "aW1wb3J0IG9yZy5qdW5pdC5UZXN0OwoKaW1wb3J0IHN0YXRpYyBvcmcuanVuaXQuQXNzZXJ0Lio7CgpwdWJsaWMgY2xhc3MgSW5zZXJ0aW9uU29ydFRlc3QgewoKICAgIEBUZXN0CiAgICBwdWJsaWMgdm9pZCBzb3J0KCkgewogICAgfQp9";

function Workspace() {

    const [isLoading, setLoading] = useState(false);
    const editorRef = useRef(null);


    function handleEditorDidMount(editor) {
        editorRef.current = editor;
    }

    async function sendTestContent() {
        await axios.post("/api/evaluate", {taskId: 1, encodedTestContent: btoa(editorRef.current.getValue())})
            .then((response) => {
                console.log(response)
            })
            .catch((reason) => {
                console.log(reason);
            })
    }

    useEffect(() => {
        if (isLoading) {
            sendTestContent().then(() => {
                setLoading(false);
            });
        }
    }, [isLoading]);

    const handleClick = () => setLoading(true);


    return (
        <>
            <Editor
                height={"45vh"}
                defaultLanguage={"java"}
                theme={"vs-dark"}
                onMount={handleEditorDidMount}
                defaultValue={atob(b64string)}
            />
            <div style={{display: "flex", flexDirection: "row-reverse", margin: "1rem"}}>
                <Button
                    variant="primary"
                    disabled={isLoading}
                    onClick={!isLoading ? handleClick : null}
                >
                    {isLoading ? 'Verarbeitung läuft…' : 'Ausführen'}
                </Button>
            </div>
        </>
    )
}

export default Workspace;