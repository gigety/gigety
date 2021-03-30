import React from 'react';
import { BrowserRouter, Route, Switch, withRouter } from 'react-router-dom';
import AuthProvider from '../Auth/AuthProvider';
import Header from './Header';
import Landing from './Landing';
import Login from 'components/Auth/Login';
import Account from '../User/Account';
import SecuredRoute from '../../utils/SecuredRoute';
import UserSettings from '../User/Settings/UserSettings';
import CreateProfile from '../Profile/CreateProfile';
import CreateGig from '../Gig/CreateGig';
import GigPage from '../Gig/GigPage';
import GigPageSecure from '../Gig/GigPageSecure';
import ProfilesPage from '../Profile/ProfilesPage';
import ProfilePage from '../Profile/ProfilePage';
import AboutGigety from '../About/AboutGigety';
import DisplayGig from '../Gig/DisplayGig';
import ProfilePageSecure from '../Profile/ProfilePageSecure';
import StompClientContext from '../../contexts/StompClientContext';
import MessengerPage from 'components/Messenger/MessengerPage/MessengerPage';
const app = () => {
	return (
		<div className="ui container">
			<AuthProvider>
				<StompClientContext>
					<BrowserRouter>
						<div>
							<Header />
							<Route exact path="/" component={Landing} />
							<Route exact path="/login" component={Login} />
							<Route exact path="/profiles" component={ProfilesPage} />
							<Route exact path="/guest/profile/detail/:id" component={ProfilePage} />
							<Route exact path="/gigs" component={GigPage} />
							<Route exact path="/guest/gig/detail/:id" component={DisplayGig} />
							<Route exact path="/about/gigety" component={AboutGigety} />
							<Switch>
								<SecuredRoute path="/user/account" component={withRouter(Account)} />
								<SecuredRoute path="/user/settings/userSettings" component={UserSettings} />
								<SecuredRoute path="/user/profile/create" component={CreateProfile} />
								<SecuredRoute path="/user/profile/detail/:id" component={ProfilePageSecure} />
								<SecuredRoute path="/user/gig/create" component={CreateGig} />
								<SecuredRoute path="/user/gig/detail/:id" component={GigPageSecure} />
								<SecuredRoute path="/user/gigs" component={GigPageSecure} />
								<SecuredRoute path="/user/messenger/page" component={MessengerPage} />
							</Switch>
						</div>
					</BrowserRouter>
				</StompClientContext>
			</AuthProvider>
		</div>
	);
};
export default app;
