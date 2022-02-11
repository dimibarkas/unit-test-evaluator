import Editor from "@monaco-editor/react";
import {Button} from "react-bootstrap";
import SubmitButton from "./SubmitButton";

const b64string = "aW1wb3J0IG9yZy5qdW5pdC5UZXN0OwoKaW1wb3J0IHN0YXRpYyBvcmcuanVuaXQuQXNzZXJ0Lio7CgpwdWJsaWMgY2xhc3MgSW5zZXJ0aW9uU29ydFRlc3QgewoKICAgIEBUZXN0CiAgICBwdWJsaWMgdm9pZCBzb3J0KCkgewogICAgfQp9";

function Workspace() {
    return (
        <>
            <div style={{display: "flex", flexDirection: "row-reverse", marginRight: "1rem", marginBottom: "1rem"}}>
                <SubmitButton />
            </div>
            <Editor
                height={"35vh"}
                defaultLanguage={"java"}
                theme={"vs-dark"}
                defaultValue={atob(b64string)}
            />

        </>
    )
}

export default Workspace;