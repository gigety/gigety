import { Model } from './base';
/* @flow */
export class UserProfile extends Model {
	getDefaults() {
		return {
			createdDate: '',
			firstName: '',
			lastName: '',
			profileName: '',
			email: '',
			title: '',
			description: '',
			profileLocations: [],
			profileImage: {},
		};
	}
}
