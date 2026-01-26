
import api from './axios';
import { DartApiResponse, LoginResponse, UserRequest } from './types';

export const getUsers = async () => {
	const response = await api.get('/users');
	return response.data;
};

export const loginUser = async (userRequest: UserRequest): Promise<DartApiResponse<LoginResponse>> => {
	const response = await api.post('/api/auth/login', { username: userRequest.username, password: userRequest.password });
	return response.data;
}