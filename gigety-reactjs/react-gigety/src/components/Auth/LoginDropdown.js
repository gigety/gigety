import React from 'react';
import { Dropdown } from 'semantic-ui-react';
import { GIGETY_AUTH_URL, GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL } from '../../constants';

const LoginDropdown = () => (
	<Dropdown item simple direction="right" text="Login">
		<Dropdown.Menu>
			<Dropdown.Item key="gigety-login" icon="sign-in" text="Gigety" href={GIGETY_AUTH_URL} />
			<Dropdown.Item key="facebook-login" icon="facebook" text="Facebook" href={FACEBOOK_AUTH_URL} />
			<Dropdown.Item key="google-login" icon="google" text="Google" href={GOOGLE_AUTH_URL} />
		</Dropdown.Menu>
	</Dropdown>
);

export default LoginDropdown;
