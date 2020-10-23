import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import LoginDropdown from '../Auth/LoginDropdown';
import ProfileDropdown from '../Auth/ProfileDropdown';
import { Icon } from 'semantic-ui-react';
const Header = (props) => {
	console.log(`auth:: ${props.isAuthenticated}`);
	const signinSignout = props.isAuthenticated ? <ProfileDropdown /> : <LoginDropdown />;
	const header = props.isAuthenticated ? (
		<>
			<Link to="/user/Account" className="item">
				<Icon className="user circle" />
				Account
			</Link>
			<Link to="/user/gigs" className="item">
				<Icon className="industry" />
				Gigs
			</Link>
			<Link to="/profiles" className="item">
				<Icon className="address book" />
				Profiles
			</Link>
		</>
	) : (
		<>
			<Link to="/gigs" className="item">
				Find a Gig
			</Link>
			<Link to="/profiles" className="item">
				Search Profiles
			</Link>
		</>
	);
	return (
		<div className="ui massive menu">
			<div className="left menu">
				<Link to="/" className="item">
					<span role="img" aria-label="tent">
						🐸{'    '}
					</span>
					{'   '}
					&nbsp; Gigety
				</Link>
			</div>

			{header}
			<div className="right menu">{signinSignout}</div>
		</div>
	);
};
const mapStateToProps = (state) => ({
	isAuthenticated: state.authentication.validToken,
});
export default connect(mapStateToProps)(Header);
