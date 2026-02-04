
import api from './axios';
import { FeatureFlags } from './constants';
import { CommunityRequest, CommunityResponse, DartApiResponse, LoginResponse, PageResponse, UserRequest } from './types';

export const getUsers = async (): Promise<any> => {
	if(FeatureFlags.ENABLE_OFFLINE_MODE){
		return [];
	}
	const response = await api.get('/users');
	return response.data;
};

export const loginUser = async (userRequest: UserRequest): Promise<DartApiResponse<LoginResponse>> => {
	if(FeatureFlags.ENABLE_OFFLINE_MODE){
		return {
			success: true,
			data: {
				token: 'dummy-offline-token'
			},
			message: 'Logged in offline mode'
		};
	}
	const response = await api.post('/api/auth/login', { username: userRequest.username, password: userRequest.password });
	return response.data;
}

export const registerUser = async (userRequest: UserRequest): Promise<DartApiResponse<LoginResponse>> => {
	if(FeatureFlags.ENABLE_OFFLINE_MODE){
		// Simulate a successful registration in offline mode
		return {
			success: true,
			data: {
				token: 'dummy-offline-token'
			},
			message: 'Registered in offline mode'
		};
	}
	const response = await api.post('/api/auth/register', { username: userRequest.username, password: userRequest.password });
	return response.data;
}

export const logoutUser = async (): Promise<DartApiResponse<any>> => {
	if(FeatureFlags.ENABLE_OFFLINE_MODE){
		return {
			success: true,
			data: {},
			message: 'Logged out in offline mode'
		};
	}
	const response = await api.post('/api/auth/logout');
	return response.data;
}

export const createCommunity = async (data: CommunityRequest): Promise<DartApiResponse<CommunityResponse>> => {
	if(FeatureFlags.ENABLE_OFFLINE_MODE){
		return {
			success: true,
			data: { id: 'offline-' + Date.now(), ...data },
			message: 'Community created in offline mode'
		};
	}
	const response = await api.post('/api/communities', data);
	return response.data;
}


export const getCommunities = async (q: string, page: number, size: number) : Promise<DartApiResponse<PageResponse<CommunityResponse>>> => {
	if(FeatureFlags.ENABLE_OFFLINE_MODE){
		return {
			success: true,
			data: {
				content: [],
				totalElements: 0,
				totalPages: 0,
				currentPage: page
			},
			message: 'Fetched communities in offline mode'
		};
	}
	const response = await api.get('/api/communities/search', {
		params: {
			q: q,
			page: page,
			size: size
		}
	});
	return response.data;
}

export const fetchCommunityDetails = async (communityId: string) : Promise<DartApiResponse<CommunityResponse>> => {
	if(FeatureFlags.ENABLE_OFFLINE_MODE){
		return {
			success: true,
			data: {
				id: communityId,
				name: 'Offline Community',
				description: 'This is a community in offline mode'
			},
			message: 'Fetched community details in offline mode'
		};
	}
	const response = await api.get(`/api/communities/${communityId}`);
	return response.data;
}

export const updateCommunity = async (communityId: string | null, data: CommunityRequest): Promise<DartApiResponse<CommunityResponse>> => {
	if(FeatureFlags.ENABLE_OFFLINE_MODE){
		return {
			success: true,
			data: { id: communityId as string, ...data },
			message: 'Community updated in offline mode'
		};
	}
	const response = await api.put(`/api/communities/${communityId}`, data);
	return response.data;
}