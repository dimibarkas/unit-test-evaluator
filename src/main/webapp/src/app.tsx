import NavigationBar from "./components/navigation-bar";
import React, {useEffect} from "react";
import {fetchTasksIfNeeded} from "./redux/actions";
import {useDispatch} from "react-redux";

function App() {

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(fetchTasksIfNeeded())
    }, [dispatch])

    return (
        <NavigationBar/>
    );
}

export default App;
