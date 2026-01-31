export interface DartApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface User {
  username: string;
  email: string;
}

export interface LoginResponse {
  token: string;
}

// User DTOs
export interface UserResponse {
  id: string;
  username: string;
  roles: RoleResponse[];
}

export interface UserRequest {
  username: string;
  password: string;
}

// Role DTOs
export interface RoleResponse {
  id: string;
  name: string;
  permissions: PermissionResponse[];
}

export interface RoleRequest {
  name: string;
}

// Permission DTOs
export interface PermissionResponse {
  id: string;
  name: string;
}

export interface PermissionRequest {
  name: string;
}

// Todo DTOs
export interface TodoResponse {
  id: string;
  description: string;
  completed: boolean;
  createdAt: string; // ISO string
  updatedAt: string; // ISO string
  createdBy: string;
  updatedBy: string;
  markedForDeletion: boolean;
}

export interface TodoRequest {
  description: string;
  completed: boolean;
}

// Comment DTOs
export interface CommentResponse {
  id: string;
  content: string;
  targetId: string;
  targetType: string; // EEntityTargetType
  authorId: string;
  markedForDeletion: boolean;
}

export interface CommentRequest {
  content: string;
  targetId: string;
  targetType: string; // EEntityTargetType
}

// Community DTOs
export interface CommunityResponse extends AuditInfo {
  name: string;
  description: string;
}

export interface AuditInfo {
  id: string;
  markedForDeletion: boolean;
  createdAt: string; 
  updatedAt: string; 
  createdByUserInfo: UserResponse;
  updatedByUserInfo: UserResponse;
}

export interface CommunityRequest {
  name: string;
  description: string;
}

// Post DTOs
export interface PostResponse {
  id: string;
  title: string;
  content: string;
  communityId: string;
  authorId: string;
  markedForDeletion: boolean;
}

export interface PostRequest {
  title: string;
  content: string;
  communityId: string;
}

// Vote DTOs
export interface VoteResponse {
  id: string;
  targetId: string;
  targetType: string;
  userId: string;
  upvote: boolean;
}

export interface VoteRequest {
  targetId: string;
  targetType: string;
  userId: string;
  upvote: boolean;
}