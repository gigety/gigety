import React from 'react';
import { Container, Grid, GridRow, GridColumn, Segment, SegmentGroup, Icon, Button } from 'semantic-ui-react';
import { useGigUserProfile } from '../../redux/hooks/useGigUser';
import '../Styles/TextStyles.css';
import ProfileCard from './ProfileCard';
import ProfileMap from '../Maps/ProfileMap';
import { useHistory } from 'react-router-dom';
import ChatModal from '../Messenger/ChatModal/ChatModal';
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
					<ChatModal profile={profile} />
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
