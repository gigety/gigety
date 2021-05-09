import React from 'react';
import ReactDOM from 'react-dom';
import App from 'components/Layout/App';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(<App />, document.querySelector('#root'));
serviceWorkerRegistration.register();
reportWebVitals(console.log);
