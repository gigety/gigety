import React from 'react';
import { useSelector } from 'react-redux';
import { Grid, GridColumn, GridRow } from 'semantic-ui-react';
import GigList from '../GigList';
import SearchGigsInput from './SearchGigsInput';
const SearchGigs = () => {
	const { gig } = useSelector((state) => state);
	return (
		<>
			<Grid>
				<GridRow>
					<GridColumn>
						<SearchGigsInput />
					</GridColumn>
				</GridRow>
			</Grid>
			{gig && gig.gigSearchResults ? (
				<Grid>
					<GridRow>
						<GridColumn>
							<GigList gigList={gig.gigSearchResults} />
						</GridColumn>
					</GridRow>
				</Grid>
			) : (
				''
			)}
		</>
	);
};

SearchGigs.propTypes = {};

export default SearchGigs;
