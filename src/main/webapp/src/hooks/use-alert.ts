import {useEffect, useState} from "react";
import {BuildSummary, SubmissionResult} from "../model/types";

export default function useAlert() {

    const [showAlert, setShowAlert] = useState<boolean>(false);
    const [header, setHeader] = useState("");
    const [variant, setVariant] = useState("");
    const [output, setOutput] = useState("");
    const [showVideoPlayer, setShowVideoPlayer] = useState(false);
    const [videoTitle, setVideoTitle] = useState("");

    const toggleSetShowVideoPlayer = () => {
        setShowVideoPlayer(!showVideoPlayer);
    }

    const showCustomAlert = (submissionResult: SubmissionResult) => {
        let headerString = "";
        let variant = "";
        switch (submissionResult.summary) {
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
        if(submissionResult.feedback !== null) {
            setVideoTitle(submissionResult.feedback);
            setShowVideoPlayer(true);
        } else {
            setVideoTitle("");
            setShowVideoPlayer(false);
        }
        setHeader(headerString);
        setVariant(variant);
        setOutput(submissionResult.output);
        setShowAlert(true);
    }

    return {
        showAlert, setShowAlert, showCustomAlert, toggleSetShowVideoPlayer,  header, variant, output, showVideoPlayer, videoTitle
    }
}
