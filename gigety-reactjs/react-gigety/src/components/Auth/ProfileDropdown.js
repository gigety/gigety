import React from 'react';
import { connect } from 'react-redux';
import { Dropdown, Image, Icon } from 'semantic-ui-react';
import { logout } from '../../redux/actions/auth';
import { useGigUser } from 'redux/hooks/useGigUser';

const ProfileDropdown = props => {
	const { giguser } = useGigUser();

	let profileMenu;
	if (giguser) {
		const imageIcon = giguser.imageUrl ? (
			<Image avatar src={giguser.imageUrl} />
		) : (
			<Icon color="blue" name="user circle" />
		);
		profileMenu = (
			<Dropdown
				item
				simple
				trigger={
					<span>
						{imageIcon} {giguser.email}
					</span>
				}
				direction="right"
				icon={null}
			>
				<Dropdown.Menu>
					<Dropdown.Item key="user" text="Account" icon="user" />
					<Dropdown.Item key="settings" text="Settings" icon="settings" />
					<Dropdown.Item key="sign-out" text="Logout" icon="sign out" onClick={props.logout} />
				</Dropdown.Menu>
			</Dropdown>
		);
	} else {
		profileMenu = <Dropdown item simple />;
	}

	return [profileMenu];
};

export default connect(
	null,
	{ logout }
)(ProfileDropdown);
