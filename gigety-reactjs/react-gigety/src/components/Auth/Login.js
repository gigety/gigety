import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { FACEBOOK_AUTH_URL } from '../../constants/index';
import { loginAction } from '../../actions/auth';
import fbLogo from 'img/fb-logo.png';
import { Button } from 'semantic-ui-react';
const Login = props => {
	const handleSubmit = () => {
		console.log('howdy');
		props.loginAction();
	};

	return (
		<div>
			<a className={''} href={FACEBOOK_AUTH_URL}>
				<img src={fbLogo} alt="Facebook" />
				Signup with Facebook
			</a>
			<Link to={FACEBOOK_AUTH_URL} />
			<Button onClick={handleSubmit}>toke</Button>
		</div>
	);
};
export default connect(
	null,
	{ loginAction }
)(Login);
