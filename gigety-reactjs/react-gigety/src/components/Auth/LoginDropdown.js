import React from 'react';
import { Link } from 'react-router-dom';
import { Dropdown } from 'semantic-ui-react';
import { GIGETY_AUTH_URL } from '../../constants';

const LoginDropdown = () => (
	<Dropdown item simple direction="right" text="Login">
		<Dropdown.Menu>
			<Dropdown.Item key="gigety-login" icon="sign-in" text="Gigety" href={GIGETY_AUTH_URL} />
			<Dropdown.Item icon="facebook" text="Facebook" />
			<Dropdown.Item icon="google" text="Google" />
		</Dropdown.Menu>
	</Dropdown>
);

export default LoginDropdown;
