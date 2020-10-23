import React from 'react';
import { useDispatch } from 'react-redux';
import { Input } from 'semantic-ui-react';
import { searchAllProfiles } from '../../../redux/actions/guest/gigProfileAction';
const SearchProfilesInput = (props) => {
	const dispatch = useDispatch();
	const onEnter = (e) => {
		if (e.charCode === 13) {
			dispatch(searchAllProfiles(e.target.value));
		}
	};
	return (
		<>
			<Input fluid icon="search" placeholder="Search Profiles..." onKeyPress={onEnter} />
		</>
	);
};

SearchProfilesInput.propTypes = {};

export default SearchProfilesInput;
