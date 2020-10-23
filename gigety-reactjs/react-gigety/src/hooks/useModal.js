import { useState } from 'react';

const useModal = (inititialState = false) => {
	const [state, setState] = useState(inititialState);
	const setModalOpen = () => {
		setState(!state);
	};
	return [state, setModalOpen];
};
export default useModal;
