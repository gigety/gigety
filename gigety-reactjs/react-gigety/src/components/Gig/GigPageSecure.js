import React from 'react';
import PropTypes from 'prop-types';
import { Button, Container, Divider, Grid, GridColumn, GridRow } from 'semantic-ui-react';
import { Link } from 'react-router-dom';
import SearchGigs from './Search/SearchGigs';

const GigPage = (props) => {
	const onClickMyGigs = (e) => {
		console.log('Search for gigs created by logged in user from mongo');
	};
	return (
		<Container fluid>
			<Grid celled>
				<Grid.Row>
					<GridColumn width="3">
						<Button as={Link} to="/user/gig/create" primary fluid>
							Create a Gig
						</Button>
						<Divider hidden />
						<GridRow>
							<GridColumn width="3">
								<Button onClick={onClickMyGigs} primary fluid>
									Gigs Created by me
								</Button>
							</GridColumn>
						</GridRow>
						<Divider hidden />
						<GridRow>
							<GridColumn width="3">
								<Button onClick={onClickMyGigs} primary fluid>
									Gigs I am assigned to
								</Button>
							</GridColumn>
						</GridRow>
					</GridColumn>
					<GridColumn width="13">
						<SearchGigs />
					</GridColumn>
				</Grid.Row>
			</Grid>
		</Container>
	);
};

GigPage.propTypes = {};

export default GigPage;
