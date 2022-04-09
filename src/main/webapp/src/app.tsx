import NavigationBar from "./components/navigation-bar";
import React, {useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import TaskContainer from "./components/task-container";
import {fetchTasksIfNeeded} from "./redux/actions/tasks";
import {fetchUserIfNeeded} from "./redux/actions/user";
import {fetchProgressList, fetchProgressListIfNeeded} from "./redux/actions/progress";
import {State} from "./redux/reducers";

function App() {

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(fetchTasksIfNeeded())
        dispatch(fetchUserIfNeeded())
        dispatch(fetchProgressListIfNeeded())
    }, [dispatch])

    return (
        <>
            <NavigationBar/>
            <TaskContainer/>
        </>
    );
}

export default App;
