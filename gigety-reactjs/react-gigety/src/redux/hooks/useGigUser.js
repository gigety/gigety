import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getCurrentUser } from 'redux/actions/gigUser';

export const useGigUser = () => {
	const content = useSelector(state => state.giguser);
	console.log(`content:`, content);
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(getCurrentUser());
	}, [dispatch]);

	return content;
};
