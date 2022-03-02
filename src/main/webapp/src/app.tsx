import NavigationBar from "./components/navigation-bar";
import React, {useEffect} from "react";
import {useDispatch} from "react-redux";
import {fetchTasksIfNeeded} from "./redux/actions";

function App() {

    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(fetchTasksIfNeeded())
    }, [])

    return (
        <NavigationBar/>
    );
}

export default App;
