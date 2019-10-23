import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

const Landing = props => {
	console.log('props', props);
	const landingPage =
		props.authentication && props.authentication.validToken ? <div>Secure Landing</div> : <div>Landing</div>;
	return landingPage;
};

Landing.propTypes = {
	errors: PropTypes.object.isRequired,
	authentication: PropTypes.object.isRequired,
};
const mapStateToProps = state => ({
	authentication: state.authentication,
	errors: state.errors,
});

export default connect(
	mapStateToProps,
	null
)(Landing);
