import { Model, List } from './base';
/* @flow */
export class ProfileLocation extends Model {
	getDefaults() {
		return {
			location: {
				lat: 0,
				lng: 0,
			},
			address: '',
			radius: 0,
		};
	}
}
export class ProfileLocationList extends List {
	getProfileLocations() {
		return ProfileLocation;
	}
}
