import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getGigForGuest } from '../actions/guest/gigAction';

export const useGig = (id) => {
	console.log('what the fuuuuuuuuu');
	const content = useSelector((state) => state.gig);
	const dispatch = useDispatch();
	console.log(`howdy ${id}`);
	console.log(`shit ${content}`, content);
	useEffect(() => {
		console.log('deammmmmm');
		dispatch(getGigForGuest(id));
	}, [dispatch]);

	return content.gig;
};
