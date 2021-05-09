import axios from 'axios';
import { GIGETY_MESSENGER_URL } from 'constants/index';
export default axios.create({
	baseURL: GIGETY_MESSENGER_URL,
});
