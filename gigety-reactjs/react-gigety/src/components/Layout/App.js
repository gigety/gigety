import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import Root from 'components/Layout/Root';
import Header from './Header';
import Landing from './Landing';
import Login from 'components/Auth/Login';
import Oauth2RedirectHandler from '../Auth/Oauth2RedirectHandler';
const app = () => {
	return (
		<div className="ui container">
			<Root>
				<BrowserRouter>
					<div>
						<Header />
						<Route exact path="/" component={Landing} />
						<Route exact path="/login" component={Login} />
						<Route path="/oauth2/redirect" component={Oauth2RedirectHandler} />
						<Switch></Switch>
					</div>
				</BrowserRouter>
			</Root>
		</div>
	);
};
export default app;
