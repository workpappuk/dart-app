import { CommunityResponse, DartApiResponse, PageResponse } from "./types";

export const mockSuccessfulLoginResponse = {
    success: true,
    data: {
        token: 'mock-token',
    },
    message: 'Mock login successful',
};

export const mockSuccessfulRegisterResponse = {
    success: true,
    data: {
        token: 'mock-token',
    },
    message: 'Mock registration successful',
};

export const mockSuccessfulLogoutResponse = {
    success: true,
    data: {},
    message: 'Mock logout successful',
};

export const MOCK_COMMUNITY_1 = {
    id: '1',
    name: 'Mock Community 1',
    description: 'This is a mock community for testing.',
    createdAt: new Date().toISOString(),
    markedForDeletion: false,
    createdByUserInfo: {
        id: 'user1',
        username: 'mockuser1',
        roles: [],
    },
    updatedAt: new Date().toISOString(),
    updatedByUserInfo: {
        id: 'user2',
        username: 'mockuser2',
        roles: [],
    },
};

export const MOCK_COMMUNITY_2 = {
    id: '2',
    name: 'Mock Community 2',
    description: 'This is another mock community for testing.',
    createdAt: new Date().toISOString(),
    markedForDeletion: false,
    createdByUserInfo: {
        id: 'user3',
        username: 'mockuser3',
        roles: [],
    },
    updatedAt: new Date().toISOString(),
    updatedByUserInfo: {
        id: 'user4',
        username: 'mockuser4',
        roles: [],
    },
};

export const mockCommunitiesResponse: DartApiResponse<PageResponse<CommunityResponse>> = {
    success: true,
    data: {
        content: [
            MOCK_COMMUNITY_1,
            MOCK_COMMUNITY_2
        ],
        totalElements: 2,
        totalPages: 1,
        size: 5,
        page: 0,
    },
    message: 'Mock communities fetched successfully',
};