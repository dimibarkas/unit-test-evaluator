import NavigationBar from "./components/navigation-bar";
import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import TaskContainer from "./components/task-container";
import {fetchTasksIfNeeded} from "./redux/actions/tasks";
import {fetchUserIfNeeded} from "./redux/actions/user";
import {fetchProgressListIfNeeded} from "./redux/actions/progress";
import {State} from "./redux/reducers";
import RegistrationPage from "./components/registration-page";
import {useSearchParams} from "react-router-dom";

function App() {

    const dispatch = useDispatch()
    const user = useSelector((state: State) => state.user);

    const [searchParams] = useSearchParams()
    const studentNumber = searchParams.get("studentNumber");
    const authKey = searchParams.get("authKey");

    useEffect(() => {
        if (authKey != null && studentNumber != null) {
            dispatch(fetchTasksIfNeeded(studentNumber, authKey))
            dispatch(fetchUserIfNeeded())
            dispatch(fetchProgressListIfNeeded())
        }

    }, [authKey, dispatch])


    if (user.isAuthenticated === false) {
        return (
            <>
                <NavigationBar/>
                <RegistrationPage/>
            </>
        )
    }


    return (
        <>
            <NavigationBar/>
            <TaskContainer/>
        </>
    );
}

export default App;
