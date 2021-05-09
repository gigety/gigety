import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getGigForGuest } from '../actions/guest/gigAction';

export const useGig = (id) => {
	const content = useSelector((state) => state.gig);
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(getGigForGuest(id));
	}, [dispatch]);

	return content.gig;
};
