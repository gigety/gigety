import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getCurrentUser, getUserAccount } from 'redux/actions/gigUserAction';
import { getUserProfile } from '../actions/gigProfileAction';

export const useGigUser = () => {
	const content = useSelector((state) => state.giguser);
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(getCurrentUser());
	}, [dispatch]);

	return content.giguser;
};

export const useGigUserAccount = () => {
	const content = useSelector((state) => state.giguserAccount);
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(getUserAccount());
	}, [dispatch]);
	return content.giguserAccount;
};

export const useGigUserProfile = (id) => {
	const content = useSelector((state) => state.giguserProfile);
	const dispatch = useDispatch();
	console.log(`shit ${content}`, content);
	useEffect(() => {
		dispatch(getUserProfile(id));
	}, [dispatch]);
	return content.giguserProfile;
};
