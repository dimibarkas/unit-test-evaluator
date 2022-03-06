import {useState} from "react";
import {BuildSummary, TestResult} from "../model/types";

export default function useAlert() {

    const [showAlert, setShowAlert] = useState<boolean>(false);
    const [header, setHeader] = useState("");
    const [variant, setVariant] = useState("");
    const [output, setOutput] = useState("");

    const showCustomAlert = (testResult: TestResult) => {
        let headerString = "";
        let variant = "";
        switch (testResult.summary) {
            case BuildSummary.BUILD_FAILED:
                headerString = "Build fehlgeschlagen!"
                variant = "danger";
                break
            case BuildSummary.TESTS_FAILED:
                headerString = "Build erfolgreich - Tests fehlgeschlagen!"
                variant = "warning";
                break
            case BuildSummary.BUILD_SUCCESSFUL:
                headerString = "Build erfolgreich!"
                variant = "success";
                break
            default:
                break

        }
        setHeader(headerString);
        setVariant(variant);
        setOutput(testResult.output);
        setShowAlert(true);
    }

    return {
        showAlert, setShowAlert, showCustomAlert, header, variant, output
    }
}
