import Editor from "@monaco-editor/react";
import {useEffect, useRef, useState} from "react";
import {Button} from "react-bootstrap";
import axios from "axios";

const b64string = "aW1wb3J0IG9yZy5qdW5pdC5UZXN0OwoKaW1wb3J0IHN0YXRpYyBvcmcuanVuaXQuQXNzZXJ0Lio7CgpwdWJsaWMgY2xhc3MgSW5zZXJ0aW9uU29ydFRlc3QgewoKICAgIEBUZXN0CiAgICBwdWJsaWMgdm9pZCBzb3J0KCkgewogICAgfQp9";

function Workspace() {

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

    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
        if (isLoading) {
            sendTestContent().then(() => {
                setLoading(false);
            });
        }
    }, [isLoading]);

    const handleClick = () => setLoading(true);


    //TODO: The submit button should be blue if the user can submit.
    // If a user submits his code, the button turns grey while the test are executing.
    // If the build failed, the button should turn red and stay red until the user makes changes in his code.
    // if the tests are incorrect, the button should turn yellow until the user makes changes in his code.

    return (
        <>
            <div style={{display: "flex", flexDirection: "row-reverse", marginRight: "1rem", marginBottom: "1rem"}}>

                <Button
                    variant="primary"
                    disabled={isLoading}
                    onClick={!isLoading ? handleClick : null}
                >
                    {isLoading ? 'Verarbeitung läuft…' : 'Ausführen'}
                </Button>
            </div>
            <Editor
                height={"35vh"}
                defaultLanguage={"java"}
                theme={"vs-dark"}
                onMount={handleEditorDidMount}
                defaultValue={atob(b64string)}
            />

        </>
    )
}

export default Workspace;