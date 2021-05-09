import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import SearchProfiles from '../Profile/Search/SearchProfiles';
const Landing = (props) => {
	const landingPage =
		props.authentication && props.authentication.validToken ? (
			<div>
				<SearchProfiles />
			</div>
		) : (
			<div>
				<SearchProfiles />
			</div>
		);
	return landingPage;
};

Landing.propTypes = {
	//errors: PropTypes.object.isRequired,
	authentication: PropTypes.object.isRequired,
};
const mapStateToProps = (state) => ({
	authentication: state.authentication,
	//errors: state.errors,
});

export default connect(mapStateToProps, null)(Landing);
