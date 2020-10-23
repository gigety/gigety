import React, { useState, useEffect } from 'react';
import { Container, Segment, Header, Icon, Button, Form, TextArea, Grid, Input, GridColumn } from 'semantic-ui-react';
import _ from 'lodash';
import LocationMap from '../Maps/LocationMap';
import { useDispatch, useSelector } from 'react-redux';

import { useHistory } from 'react-router-dom';
import { postGig } from '../../redux/actions/gigAction';
import LocationList from '../Profile/LocationList';
const CreateGig = () => {
	const [includeLocation, setIncludeLocation] = useState(false);
	const [gig, setGig] = useState({});
	const [locations, setLocations] = useState([]);
	const [markers, setMarkers] = useState([]);
	const [circles, setCircles] = useState([]);
	const reduxDispatch = useDispatch();
	const history = useHistory();
	const { giguser } = useSelector((state) => state.giguser);

	useEffect(() => {
		setGig((gig) => ({ ...gig, userImageUrl: giguser.imageUrl }));
	}, [giguser.imageUrl]);

	const showMap = (includeLocation) => {
		setIncludeLocation(!includeLocation);
	};

	const onSubmit = (e, gig) => {
		reduxDispatch(postGig(JSON.stringify(gig), history));
	};

	const onAddLocation = (location, miles) => {
		if (location) {
			const loc = {
				ref_id: _.random(11, 12, true),
				address: location.formatted_address,
				location: { lat: location.geometry.location.lat(), lng: location.geometry.location.lng() },
				radius: miles,
			};
			setLocations([...locations, loc]);
			setGig({
				...gig,
				gigLocations: [...locations, loc],
			});
			return loc;
		}
	};

	const onRemoveLocation = (locations, markers, circles) => (ref_id) => {
		const locs = locations.filter((location) => {
			return location.ref_id !== ref_id;
		});
		setLocations(locs);
		setGig({ ...gig, gigLocations: locs });
		markers.map((marker) => {
			if (marker.ref_id === ref_id) {
				marker.setMap(null);
			}
			return marker;
		});
		circles.map((circle) => {
			if (circle.ref_id === ref_id) {
				circle.setMap(null);
			}
			return circle;
		});
	};

	return (
		<Container fluid>
			<Form encType="multipart/form-data">
				<Segment>
					<Grid>
						<Grid.Row>
							<Grid.Column>
								<Header as="h2">
									<Icon name="edit" />
									<Header.Content>Create a Gig</Header.Content>
								</Header>
							</Grid.Column>
						</Grid.Row>
						<Grid.Row>
							<Grid.Column width="10">
								<Segment>
									<Form.Field>
										<label>Title</label>
										<Input
											placeholder="Enter title for your new profile..."
											onBlur={(e) => {
												setGig({ ...gig, title: e.target.value });
											}}
										/>
									</Form.Field>
									<Form.Field>
										<label>Description</label>
										<TextArea
											placeholder="A brief description of this profile..."
											onBlur={(e) => {
												setGig({
													...gig,
													description: e.target.value,
												});
											}}
										/>
									</Form.Field>
								</Segment>
								<Form.Field>
									<label>Locations</label>
									<LocationList
										locations={locations}
										onRemove={onRemoveLocation(locations, markers, circles)}
									/>
								</Form.Field>
							</Grid.Column>
						</Grid.Row>
						<Grid.Row>
							<Grid.Column width="10">
								<Button
									type="button"
									primary
									circular
									fluid
									animated="fade"
									onClick={(e) => {
										showMap(includeLocation);
									}}
								>
									{includeLocation ? 'Close Map' : 'Add Location(s)'}
								</Button>
							</Grid.Column>
							<Grid.Column width="6">
								<Button
									type="button"
									primary
									fluid
									onClick={(e) => {
										onSubmit(e, gig);
									}}
								>
									Submit
								</Button>
							</Grid.Column>
						</Grid.Row>
						<Grid.Row>
							<Grid.Column>
								{includeLocation ? (
									<LocationMap
										addSearch
										addLocator
										onAddLocation={onAddLocation}
										markers={markers}
										setMarkers={setMarkers}
										circles={circles}
										setCircles={setCircles}
									/>
								) : (
									''
								)}
							</Grid.Column>
						</Grid.Row>
					</Grid>
				</Segment>
			</Form>
		</Container>
	);
};

export default CreateGig;
