import React from 'react';
import { Container, Grid, Button, Divider } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
import UserCard from './UserCard';
import { useSelector } from 'react-redux';
import { useGigUserAccount } from '../../redux/hooks/useGigUser';
import ProfileList from '../Profile/ProfileList';
const Account = () => {
	const { giguser } = useSelector((state) => state.giguser);
	const giguserAccount = useGigUserAccount();
	const list = giguserAccount ? <ProfileList profileList={giguserAccount.allProfiles} /> : <ProfileList />;
	const account = giguser ? (
		<Container fluid>
			<Grid celled>
				<Grid.Row>
					<Grid.Column width={4}>
						<UserCard giguser={giguser} />
					</Grid.Column>
					<Grid.Column width={6}>
						<Grid.Row>
							<Button as={Link} to="/user/profile/create" primary>
								Create a Gigety Profile
							</Button>
						</Grid.Row>
						<Divider hidden />
						<Grid.Row>
							<Grid.Column>{list}</Grid.Column>
						</Grid.Row>
					</Grid.Column>
				</Grid.Row>
				<Grid.Row></Grid.Row>
			</Grid>
		</Container>
	) : (
		<div></div>
	);
	return account;
};

export default Account;
