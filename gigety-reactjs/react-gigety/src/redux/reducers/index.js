import { combineReducers } from 'redux';
import authentication from './authenticationReducer';
import giguser from './gigUserReducer';
import giguserAccount from './gigUserAccountReducer';
import giguserProfile from './gigProfileReducer';
import gig from './gigReducer';
import location from './locationReducer';
import messagesReducer from './messegesReducer';
export default combineReducers({
	authentication: authentication,
	giguser: giguser,
	giguserAccount: giguserAccount,
	giguserProfile: giguserProfile,
	gig: gig,
	currentAddress: location,
	messages: messagesReducer,
});
