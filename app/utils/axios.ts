
import axios from 'axios';
import { AuthorizationKey } from './constants';
import { isNil } from "lodash";

const api = axios.create({
	baseURL: 'http://localhost:8080',
	timeout: 60000,
	headers: {
		"Content-Type": "application/json",
	},
});

export const setUserSessionToken = (token?: string | null) => {
	if (!isNil(token)) {
		api.defaults.headers.common[AuthorizationKey] = `Bearer ${token}`;
	}
};

export const clearUserSessionToken = () => {
	delete api.defaults.headers.common[AuthorizationKey];
};

export default api;
