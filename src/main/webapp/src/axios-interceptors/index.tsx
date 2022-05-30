//response interceptor
import {AxiosResponse} from "axios";
import {setAuthenticationError} from "../redux/actions/user";
import {store} from "../redux/store";

export function onFulfilled(response: AxiosResponse) {
    // console.log("Response Interceptor (fulfilled)")
    return response;
}

export function onRejected(error: any) {
    console.log("Response Interceptor (failed)")
    if(error.response.status === 403) {
        console.log(error.response.status === 403)
        // @ts-ignore
        store.dispatch(setAuthenticationError())
    }
    return Promise.reject(error);
}