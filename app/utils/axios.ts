
import axios from 'axios';
import { API_BASE_URL, AuthorizationKey } from './constants';
import { isNil } from "lodash";
import { store, setToken } from '../redux/store';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { SESSION_TOKEN_KEY } from './constants';
// Utility to force logout user (no navigation)
export const forceLogoutUser = async () => {
	store.dispatch(setToken(null));
	await AsyncStorage.removeItem(SESSION_TOKEN_KEY);
	clearUserSessionToken();
};


const api = axios.create({
	baseURL: API_BASE_URL,
	timeout: 60000,
	headers: {
		"Content-Type": "application/json",
	},
});

// Add a response interceptor to handle 403 errors globally
api.interceptors.response.use(
	(response) => response,
	(error) => {
		if (error.response && error.response.status === 403) {
			// Use shared forceLogoutUser logic
			forceLogoutUser();
		}
		return Promise.reject(error);
	}
);

export const setUserSessionToken = (token?: string | null) => {
	if (!isNil(token)) {
		api.defaults.headers.common[AuthorizationKey] = `Bearer ${token}`;
	}
};

export const clearUserSessionToken = () => {
	delete api.defaults.headers.common[AuthorizationKey];
};

export default api;
