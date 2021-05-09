import React from 'react';
import { useSelector } from 'react-redux';
import SearchProfilesInput from './SearchProfilesInput';
import { Grid, GridColumn, GridRow } from 'semantic-ui-react';
import ProfileList from '../ProfileList';
import ProfilesMap from '../../Maps/ProfilesMap';

const SearchProfiles = () => {
	const { giguserProfile } = useSelector((state) => state);
	return (
		<Grid>
			<Grid.Row>
				<Grid.Column width="8">
					<SearchProfilesInput />
				</Grid.Column>
			</Grid.Row>
			{giguserProfile && giguserProfile.profileSearchResults ? (
				<GridRow>
					<GridColumn width="8">
						<ProfileList profileList={giguserProfile.profileSearchResults} />
					</GridColumn>
					<GridColumn width="8">
						<ProfilesMap profiles={giguserProfile.profileSearchResults} />
					</GridColumn>
				</GridRow>
			) : (
				''
			)}
		</Grid>
	);
};

SearchProfiles.propTypes = {};

export default SearchProfiles;
