import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getUserProfile } from '../actions/gigProfileAction';

export const useGigUserProfile = (id) => {
	const content = useSelector((state) => state.giguserProfile);
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(getUserProfile(id));
	}, [dispatch]);
	return content.giguserProfile;
};
