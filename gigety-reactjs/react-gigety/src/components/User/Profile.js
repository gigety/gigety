import React from 'react';

import { useGigUser } from '../../hooks/useGigUser';
const Profile = () => {
	const { giguser } = useGigUser();
	return giguser ? <div> {giguser.email}</div> : <div>No profile</div>;
};

export default Profile;
