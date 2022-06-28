import NavigationBar from "./components/navigation-bar";
import React, {useEffect, useMemo} from "react";
import {useDispatch, useSelector} from "react-redux";
import TaskContainer from "./components/task-container";
import {fetchTasksIfNeeded} from "./redux/actions/tasks";
import {fetchProgressListIfNeeded} from "./redux/actions/progress";
import {State} from "./redux/reducers";
import RegistrationPage from "./components/registration-page";
import {useSearchParams} from "react-router-dom";
import {AuthCredentials} from "./model/types";
import AuthenticationError from "./components/authentication-error";
import {connect} from "react-redux";
import FinalPage from "./components/final-page";

const mapStateToProps = (state: State) => {
    return {
        hasAllTasksPassed: state.progress.hasAllTasksPassed,
        hasQuit: state.progress.hasQuit,
        hasMinTasksPassed: state.progress.hasMinTasksPassed
    }
}

function App({hasAllTasksPassed, hasQuit, hasMinTasksPassed}) {

    const dispatch = useDispatch()
    const user = useSelector((state: State) => state.user);

    const [searchParams] = useSearchParams()
    const studentId = searchParams.get("studentNumber");
    const authKey = searchParams.get("authKey");

    const authCredentials: AuthCredentials = useMemo(() => {
        return {
            authKey: authKey,
            studentId: studentId
        }
    }, [studentId, authKey])


    useEffect(() => {
        if (authCredentials.authKey !== null && authCredentials.studentId !== null) {
            dispatch(fetchTasksIfNeeded(authCredentials))
        }

        fetchProgressListIfNeeded(authCredentials)

    }, [authCredentials, dispatch])


    if (user.isAuthenticated === false && user.authenticationError === false) {
        return (
            <>
                <NavigationBar/>
                <RegistrationPage/>
            </>
        )
    } else if (user.authenticationError && user.isAuthenticated === false) {
        return (
            <>
                <NavigationBar/>
                <AuthenticationError/>
            </>
        )
    }

    if(hasAllTasksPassed || (hasMinTasksPassed && hasQuit)) {
        return (
            <>
                <NavigationBar />
                <FinalPage />
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

export default connect(mapStateToProps)(App);
