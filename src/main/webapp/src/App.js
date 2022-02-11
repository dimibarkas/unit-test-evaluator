import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import CustomNavbar from "./components/CustomNavbar";
import TaskSelection from "./components/TaskSelection";

function App() {
    return (
        <div>
            <CustomNavbar/>
            <TaskSelection/>
        </div>
    );
}

export default App;
