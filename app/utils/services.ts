
import api from './axios';
import { CommunityRequest, DartApiResponse, LoginResponse, UserRequest } from './types';

export const getUsers = async () => {
	const response = await api.get('/users');
	return response.data;
};

export const loginUser = async (userRequest: UserRequest): Promise<DartApiResponse<LoginResponse>> => {
	const response = await api.post('/api/auth/login', { username: userRequest.username, password: userRequest.password });
	return response.data;
}

export const logoutUser = async () => {
	const response = await api.post('/api/auth/logout');
	return response.data;
}

export const createCommunity = async (data: CommunityRequest) => {
	const response = await api.post('/api/communities', data);
	return response.data;
}

