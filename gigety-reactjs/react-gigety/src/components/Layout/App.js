import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import AuthProvider from '../Auth/AuthProvider';
import Header from './Header';
import Landing from './Landing';
import Login from 'components/Auth/Login';
import Oauth2RedirectHandler from '../Auth/Oauth2RedirectHandler';
import Profile from '../User/Profile';
import SecuredRoute from '../../utils/SecuredRoute';
const app = () => {
	return (
		<div className="ui container">
			<AuthProvider>
				<BrowserRouter>
					<div>
						<Header />
						<Route exact path="/" component={Landing} />
						<Route exact path="/login" component={Login} />
						<Route path="/oauth2/redirect" component={Oauth2RedirectHandler} />
						<Switch>
							<SecuredRoute path="/user/profile" component={Profile} />
						</Switch>
					</div>
				</BrowserRouter>
			</AuthProvider>
		</div>
	);
};
export default app;