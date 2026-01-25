
import api from './axios';
import { DartApiResponse, LoginResponse } from './types';

export const getUsers = async () => {
	const response = await api.get('/users');
	return response.data;
};

export const loginUser = async (username: string, password: string): Promise<DartApiResponse<LoginResponse>> => {
	const response = await api.post('/api/auth/login', { username, password });
	return response.data;
}