import React from 'react';
import { Container, Grid, GridRow, GridColumn, Segment, SegmentGroup, Icon } from 'semantic-ui-react';
import { useHistory } from 'react-router-dom';
import GigMap from '../Maps/GigMap';
import UserLabel from '../User/UserLabel';
import { useGig } from '../../redux/hooks/useGigGig';
import GigCard from './GigCard';
const DisplayGig = (props) => {
	const { id } = props.match.params;
	const gig = useGig(id);
	const history = useHistory();
	const onClickBack = (e) => {
		history.goBack();
	};
	const displayGig = gig ? (
		<Container>
			<SegmentGroup>
				<Segment>
					<Grid>
						<GridRow>
							<GridColumn width="5">
								<Icon.Group size="large" as="Button" onClick={onClickBack}>
									<Icon name="long arrow alternate left" />
									Back to results
								</Icon.Group>
								<GigCard gig={gig} />
								<UserLabel userImageUrl={gig.userImageUrl} email={gig.email} />
							</GridColumn>
							<GridColumn width="11">
								<Segment>
									<div className="title-large">{gig.description}</div>
								</Segment>
								<GigMap gigLocations={gig.gigLocations} />
							</GridColumn>
						</GridRow>
					</Grid>
				</Segment>
			</SegmentGroup>
		</Container>
	) : (
		<>Searching for gig {id}</>
	);
	return displayGig;
};

DisplayGig.propTypes = {};

export default DisplayGig;
