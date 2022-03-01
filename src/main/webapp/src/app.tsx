import NavigationBar from "./components/navigation-bar";
import TaskContainer from "./components/task-container";
import {useState} from "react";
import {Task} from "./types/Task";
import React from "react";

function App() {
    const [task, setTask] = useState<Task | undefined>(undefined);
    return (
        <>
            <NavigationBar/>

        </>
    );
}

export default App;
