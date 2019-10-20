import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

const Landing = props => {
	const landingPage =
		props.security && this.props.security.validToken ? <div>Secure Landing</div> : <div>Landing</div>;
	return landingPage;
};

Landing.propTypes = {
	errors: PropTypes.object.isRequired,
	security: PropTypes.object.isRequired,
};
const mapStateToProps = state => ({
	security: state.security,
	errors: state.errors,
});

export default connect(
	mapStateToProps,
	null
)(Landing);
