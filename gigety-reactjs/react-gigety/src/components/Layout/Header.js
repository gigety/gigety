import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { logout } from 'actions/auth';
import { Button } from 'semantic-ui-react';
const Header = props => {
	console.log(`auth:: ${props.isAuthenticated}`);
	const signinSignout = props.isAuthenticated ? (
		<Button onClick={props.logout} className="item">
			Logout
		</Button>
	) : (
		<Link to="/login" className="item">
			Login
		</Link>
	);
	return (
		<div className="ui massive menu">
			<Link to="/" className="item">
				Gigety
			</Link>
			<div className="right menu">{signinSignout}</div>
		</div>
	);
};
const mapStateToProps = state => ({
	isAuthenticated: state.authentication.validToken,
});
export default connect(
	mapStateToProps,
	{ logout }
)(Header);