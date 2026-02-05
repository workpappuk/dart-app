import { AuditInfo, CommunityResponse, DartApiResponse, PageResponse } from "./types";

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

export const MOCK_AUDITABLE_USER: AuditInfo = {
    createdAt: new Date().toISOString(),
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
    id: Math.random().toString(36).substring(2, 15),
    markedForDeletion: false,
};

export const MOCK_COMMUNITY_1 = {
    ...MOCK_AUDITABLE_USER,
    name: 'Mock Community 1',
    description: 'This is a mock community for testing.',
}

export const MOCK_COMMUNITY_2 = {
    ...MOCK_AUDITABLE_USER,
    name: 'Mock Community 2',
    description: 'This is another mock community for testing.',
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