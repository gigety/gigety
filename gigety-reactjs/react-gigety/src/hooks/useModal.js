import { useState } from 'react';

const useModal = (inititialState = false) => {
	const [state, setState] = useState(inititialState);
	const setModalState = () => {
		setState(!state);
	};
	return [state, setModalState];
};
export default useModal;
