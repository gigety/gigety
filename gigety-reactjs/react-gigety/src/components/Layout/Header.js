import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import LoginDropdown from '../Auth/LoginDropdown';
import ProfileDropdown from '../Auth/ProfileDropdown';
const Header = (props) => {
	console.log(`auth:: ${props.isAuthenticated}`);
	const signinSignout = props.isAuthenticated ? <ProfileDropdown /> : <LoginDropdown />;
	return (
		<div className="ui massive menu">
			<Link to="/" className="item">
				Gigety
			</Link>
			<div className="right menu">{signinSignout}</div>
		</div>
	);
};
const mapStateToProps = (state) => ({
	isAuthenticated: state.authentication.validToken,
});
export default connect(mapStateToProps)(Header);
