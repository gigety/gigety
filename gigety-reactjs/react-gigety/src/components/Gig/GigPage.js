import React from 'react';
import { Container, Grid, GridColumn } from 'semantic-ui-react';
import SearchGigs from './Search/SearchGigs';

const GigPage = (props) => {
	const onClickMyGigs = (e) => {};
	return (
		<Container fluid>
			<Grid celled>
				<Grid.Row>
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
