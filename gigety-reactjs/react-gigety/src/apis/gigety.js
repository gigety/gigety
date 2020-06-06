import axios from 'axios';
import { GIGETY_API_URL } from 'constants/index';
export default axios.create({
	baseURL: GIGETY_API_URL,
});
