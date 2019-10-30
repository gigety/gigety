import { combineReducers } from 'redux';
import authentication from './authenticationReducer';
import giguser from './gigUserReducer';
export default combineReducers({
	authentication: authentication,
	giguser: giguser,
});
