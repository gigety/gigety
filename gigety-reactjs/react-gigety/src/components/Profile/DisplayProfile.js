import React from 'react';
import { Container, Grid, GridRow, GridColumn, Segment, SegmentGroup, Icon, Button } from 'semantic-ui-react';
import { useGigUserProfile } from '../../redux/hooks/useGigUser';
import '../Styles/TextStyles.css';
import ProfileCard from './ProfileCard';
import UserLabel from '../User/UserLabel';
import ProfileMap from '../Maps/ProfileMap';
import { useHistory } from 'react-router-dom';
const DisplayProfile = ({ profile }) => {
	const history = useHistory();
	const onClickBack = (e) => {
		history.goBack();
	};
	const displayProfile = profile ? (
		<Grid>
			<GridRow>
				<GridColumn width="5">
					<Icon.Group size="large" as={Button} onClick={onClickBack}>
						<Icon name="long arrow alternate left" />
						Back to results
					</Icon.Group>
					<ProfileCard profile={profile} />
					<UserLabel userImageUrl={profile.userImageUrl} email={profile.email} />
				</GridColumn>
				<GridColumn width="11">
					<Segment>
						<div className="title-large">{profile.description}</div>
					</Segment>
					<ProfileMap profileLocations={profile.profileLocations} />
				</GridColumn>
			</GridRow>
		</Grid>
	) : (
		<> </>
	);
	return displayProfile;
};

DisplayProfile.propTypes = {};

export default DisplayProfile;
