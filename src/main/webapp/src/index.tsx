import React from 'react';
import ReactDOM from 'react-dom';
import App from './app';
import "./custom.scss"
import thunk from "redux-thunk";
import {createLogger} from 'redux-logger';
import {applyMiddleware, createStore, compose} from "redux";
import reducers from "./redux/reducers";
import {Provider} from "react-redux";

const middleware = [thunk]
if (process.env.NODE_ENV !== 'production') {
    middleware.push(createLogger())
}

const composeEnhancers = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const store = createStore(
    reducers, /* preloadedState, */
    composeEnhancers(applyMiddleware(...middleware))
);

ReactDOM.render(
    <Provider store={store}>
        <App/>
    </Provider>,
    document.getElementById('root')
);
