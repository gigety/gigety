import React from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { Route, Redirect } from 'react-router-dom';
const SecuredRoute = ({ component: Component, authentication, ...otherProps }) => (
	<Route
		{...otherProps}
		render={(props) => (authentication.validToken === true ? <Component {...props} /> : <Redirect to="/" />)}
	/>
);
SecuredRoute.propTypes = {
	authentication: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
	authentication: state.authentication,
});
export default connect(mapStateToProps)(SecuredRoute);
