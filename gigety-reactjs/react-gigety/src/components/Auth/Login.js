import React from 'react';
import { Link } from 'react-router-dom';
import { FACEBOOK_AUTH_URL } from 'constants/index';
import fbLogo from 'img/fb-logo.png';
const Login = () => {
	return (
		<div>
			<a className={''} href={FACEBOOK_AUTH_URL}>
				<img src={fbLogo} alt="Facebook" />
				Signup with Facebook
			</a>
			<Link to={FACEBOOK_AUTH_URL} />
		</div>
	);
};
export default Login;
