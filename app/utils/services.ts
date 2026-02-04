
import api from './axios';
import { CommunityRequest, CommunityResponse, DartApiResponse, LoginResponse, PageResponse, UserRequest } from './types';

export const getUsers = async () => {
	const response = await api.get('/users');
	return response.data;
};

export const loginUser = async (userRequest: UserRequest): Promise<DartApiResponse<LoginResponse>> => {
	const response = await api.post('/api/auth/login', { username: userRequest.username, password: userRequest.password });
	return response.data;
}

export const registerUser = async (userRequest: UserRequest): Promise<DartApiResponse<LoginResponse>> => {
	const response = await api.post('/api/auth/register', { username: userRequest.username, password: userRequest.password });
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

export const getCommunities = async () : Promise<DartApiResponse<CommunityResponse[]>> => {
	const response = await api.get('/api/communities');
	return response.data;
}

export const fetchCommunityDetails = async (communityId: string) : Promise<DartApiResponse<CommunityResponse>> => {
	const response = await api.get(`/api/communities/${communityId}`);
	return response.data;
}

export const updateCommunity = async (communityId: string | null, data: CommunityRequest) => {
	const response = await api.put(`/api/communities/${communityId}`, data);
	return response.data;
}