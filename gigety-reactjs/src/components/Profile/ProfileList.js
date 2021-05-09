import React from 'react';
import PropTypes from 'prop-types';
import ProfileLabel from './ProfileLabel';
import { List } from 'semantic-ui-react';
import '../Styles/ScrollStyles.css';
const ProfileList = ({ profileList }) => {
	return (
		<List divided verticalAlign="middle" className="scroll-list medium">
			{profileList
				? profileList.map((profile) => {
						//TODO: this is hacky, make a revisit
						const linkTo =
							window.location.pathname === '/user/Account'
								? `/user/profile/detail/${profile.id}`
								: `/guest/profile/detail/${profile.id}`;
						return <ProfileLabel key={profile.id} profile={profile} linkTo={linkTo} />;
				  })
				: ''}
		</List>
	);
};

ProfileList.propTypes = {
	profilelist: PropTypes.array,
};

export default ProfileList;
