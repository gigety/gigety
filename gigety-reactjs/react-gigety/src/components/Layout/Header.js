import React from 'react';
import { Link } from 'react-router-dom';
const Header = () => {
	return (
		<div className="ui secondary pointing menu">
			<Link to="/" className="item">
				Gigety
			</Link>
			<div className="right menu">
				<Link to="/" classname="item">
					Login
				</Link>
			</div>
		</div>
	);
};

export default Header;
