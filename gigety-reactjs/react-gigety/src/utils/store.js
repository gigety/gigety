import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import reducers from 'reducers';

const initialState = {};
const middlewares = [thunk];

const ReduxDevTools = window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

export const store =
	window.navigator.userAgent.includes('Chrome') && ReduxDevTools
		? createStore(
				reducers,
				initialState,
				compose(
					applyMiddleware(...middlewares),
					ReduxDevTools
				)
		  )
		: createStore(reducers, initialState, applyMiddleware(...middlewares));
