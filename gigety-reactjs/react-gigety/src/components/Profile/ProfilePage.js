import React from 'react';
import PropTypes from 'prop-types';
import DisplayProfile from './DisplayProfile';
import { Button } from 'semantic-ui-react';
import { useGigUserProfile } from '../../redux/hooks/useGigUser';

const ProfilePage = (props) => {
	const { id } = props.match.params;
	const profile = useGigUserProfile(id);
	return (
		<>
			<DisplayProfile profile={profile} />
		</>
	);
};

ProfilePage.propTypes = {};

export default ProfilePage;
