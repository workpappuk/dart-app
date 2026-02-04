
import api from './axios';
import { FeatureFlags } from './constants';
import { MOCK_COMMUNITY_1, MOCK_COMMUNITY_2, mockCommunitiesResponse, mockSuccessfulLoginResponse, mockSuccessfulLogoutResponse, mockSuccessfulRegisterResponse } from './mock';
import { CommunityRequest, CommunityResponse, DartApiResponse, LoginResponse, PageResponse, UserRequest } from './types';

 

export const loginUser = async (userRequest: UserRequest): Promise<DartApiResponse<LoginResponse>> => {
	if (FeatureFlags.ENABLE_OFFLINE_MODE) {
		return mockSuccessfulLoginResponse;
	}
	const response = await api.post('/api/auth/login', { username: userRequest.username, password: userRequest.password });
	return response.data;
}

export const registerUser = async (userRequest: UserRequest): Promise<DartApiResponse<LoginResponse>> => {
	if (FeatureFlags.ENABLE_OFFLINE_MODE) {
		return mockSuccessfulRegisterResponse;
	}
	const response = await api.post('/api/auth/register', { username: userRequest.username, password: userRequest.password });
	return response.data;
}

export const logoutUser = async (): Promise<DartApiResponse<any>> => {
	if (FeatureFlags.ENABLE_OFFLINE_MODE) {
		return mockSuccessfulLogoutResponse;
	}
	const response = await api.post('/api/auth/logout');
	return response.data;
}

export const createCommunity = async (data: CommunityRequest): Promise<DartApiResponse<CommunityResponse>> => {
	if (FeatureFlags.ENABLE_OFFLINE_MODE) {
		return {
			success: true,
			data: MOCK_COMMUNITY_1,
			message: 'Community created in offline mode'
		};
	}
	const response = await api.post('/api/communities', data);
	return response.data;
}


export const getCommunities = async (q: string, page: number, size: number): Promise<DartApiResponse<PageResponse<CommunityResponse>>> => {
	if (FeatureFlags.ENABLE_OFFLINE_MODE) {
		return mockCommunitiesResponse;
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

export const fetchCommunityDetails = async (communityId: string): Promise<DartApiResponse<CommunityResponse>> => {
	if (FeatureFlags.ENABLE_OFFLINE_MODE) {
		return {
			success: true,
			data: MOCK_COMMUNITY_1,
			message: 'Fetched community details in offline mode'
		};
	}
	const response = await api.get(`/api/communities/${communityId}`);
	return response.data;
}

export const updateCommunity = async (communityId: string | null, data: CommunityRequest): Promise<DartApiResponse<CommunityResponse>> => {
	if (FeatureFlags.ENABLE_OFFLINE_MODE) {
		return {
			success: true,
			data: MOCK_COMMUNITY_2,
			message: 'Fetched community details in offline mode'
		};
	}
	const response = await api.put(`/api/communities/${communityId}`, data);
	return response.data;
}