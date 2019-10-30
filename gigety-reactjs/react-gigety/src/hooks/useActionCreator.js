import { useDispatch } from 'react-redux';
import { useEffect } from 'react';
const useActionCreator = (actionCreator, resource) => {
	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(actionCreator());
	}, [resource]);
};

export default useActionCreator;
