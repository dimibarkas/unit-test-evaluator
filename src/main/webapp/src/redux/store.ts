import thunk from "redux-thunk";
import {createLogger} from 'redux-logger';
import {applyMiddleware, createStore, compose} from "redux";
import reducers from "./reducers";

const middleware = [thunk]
if (process.env.NODE_ENV !== 'production') {
    middleware.push(createLogger())
}

const composeEnhancers = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

export const store = createStore(
    reducers, /* preloadedState, */
    composeEnhancers(applyMiddleware(...middleware))
);

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch