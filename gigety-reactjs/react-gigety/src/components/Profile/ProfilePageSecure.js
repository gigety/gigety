import React, { useState } from 'react';
import PropTypes from 'prop-types';
import DisplayProfile from './DisplayProfile';
import { Button, MenuItem, Modal, Menu } from 'semantic-ui-react';
import { useGigUserProfile } from '../../redux/hooks/useGigUser';
import EditProfile from './EditProfile';
import useModal from '../../hooks/useModal';
import { useDispatch } from 'react-redux';
import { removeGigProfile } from '../../redux/actions/gigProfileAction';
import { useHistory } from 'react-router-dom';
const ProfilePageSecure = (props) => {
	const { id } = props.match.params;
	const profile = useGigUserProfile(id);
	const [activePage, setActivePage] = useState('profile');
	const dispatch = useDispatch();
	const profilePage =
		activePage === 'profile' ? <DisplayProfile profile={profile} /> : <EditProfile profile={profile} />;
	const handleItemClick = (item) => setActivePage(item);
	const [modalState, toggleModalState] = useModal();
	const removeProfile = (id) => {
		dispatch(removeGigProfile(id, history));
	};
	const history = useHistory();
	return (
		<>
			<Menu tabular>
				<MenuItem
					name="Profile"
					active={activePage === 'profile'}
					onClick={() => handleItemClick('profile')}
					icon="user"
				></MenuItem>
				<MenuItem
					name="Edit"
					active={activePage === 'edit'}
					onClick={() => handleItemClick('edit')}
					icon="edit"
				></MenuItem>
				<MenuItem>
					<Button color="red" onClick={toggleModalState}>
						Remove
					</Button>
				</MenuItem>
			</Menu>
			{profilePage}
			<Modal dimmer={'blurring'} open={modalState} onClose={null}>
				<Modal.Header>Remove Profile {profile ? profile.title : ''}</Modal.Header>
				<Modal.Content>
					This will permamently remove this profile from your account. This action is irrecoverable. Consider
					deactivating if you do not wish to lose this profile.
				</Modal.Content>
				<Modal.Actions>
					<Button negative onClick={toggleModalState}>
						Keep Profile
					</Button>
					<Button
						positive
						onClick={() => {
							toggleModalState();
							removeProfile(id);
						}}
					>
						Remove Profile
					</Button>
				</Modal.Actions>
			</Modal>
		</>
	);
};

ProfilePageSecure.propTypes = {};

export default ProfilePageSecure;
