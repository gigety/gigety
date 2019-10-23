import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
class Oauth2RedirectHandler extends Component {
	getUrlParameter(name) {
		name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
		const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
		const results = regex.exec(this.props.location.search);
		return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
	}

	render() {
		console.log(this.props.location.search);
		const token = this.getUrlParameter('token');
		console.log(`token: ${token}`);
		const error = this.getUrlParameter('error');
		console.log(`error: ${error}`);
		if (token) {
			localStorage.setItem('jwtToken', token);

			//TODO: call action to update state with authentication
			return (
				<Redirect
					to={{
						pathname: '/',
						state: { from: this.props.location },
					}}
				/>
			);
		} else {
			return (
				<Redirect
					to={{
						pathname: '/',
						state: { from: this.props.location, error: error },
					}}
				/>
			);
		}
	}
}

export default Oauth2RedirectHandler;
