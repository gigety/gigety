import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import { loginAction } from 'actions/auth';
class Oauth2RedirectHandler extends Component {
	getUrlParameter(name) {
		name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
		const regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
		const results = regex.exec(this.props.location.search);
		return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
	}
	componentDidMount() {
		console.log(this.props.location.search);
		const token = this.getUrlParameter('token');
		console.log(`token: ${token}`);
		const error = this.getUrlParameter('error');
		console.log(`error: ${error}`);
		localStorage.setItem('jwtToken', token);
		this.props.loginAction(token);
	}
	render() {
		//	if (token) {

		//TODO: call action to update state with authentication
		return (
			<Redirect
				to={{
					pathname: '/',
					state: { from: this.props.location },
				}}
			/>
		);
		//	} else {
		// return (
		// 	<Redirect
		// 		to={{
		// 			pathname: '/',
		// 			state: { from: this.props.location, error: error },
		// 		}}
		// 	/>
		// );
		//}
	}
}

export default connect(
	null,
	{ loginAction }
)(Oauth2RedirectHandler);
