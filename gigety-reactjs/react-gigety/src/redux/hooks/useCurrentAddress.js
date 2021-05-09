import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { getCurrentAddress } from '../actions/locationAction';
/**
 * Use CurrentLocation across the app to get users current location.
 * Becareful not to get location to often, . so probably will refactor
 * this to have latest location, mix that with watch location
 * @param {*} options
 */
export const useCurrentAddress = (options = {}) => {
	const content = useSelector((state) => state.currentAddress);
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(getCurrentAddress());
	}, [dispatch]);
	return content.currentAddress;
};
