import NavigationBar from "./components/navigation-bar";
import React, {useEffect} from "react";
import {useDispatch} from "react-redux";
import TaskContainer from "./components/task-container";
import {fetchTasksIfNeeded} from "./redux/actions/tasks";

function App() {

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(fetchTasksIfNeeded())
    }, [dispatch])

    return (
        <>
            <NavigationBar/>
            <TaskContainer/>
        </>
    );
}

export default App;
