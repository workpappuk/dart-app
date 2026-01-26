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
  id: number;
  username: string;
  roles: RoleResponse[];
}

export interface UserRequest {
  username: string;
  password: string;
}

// Role DTOs
export interface RoleResponse {
  id: number;
  name: string;
  permissions: PermissionResponse[];
}

export interface RoleRequest {
  name: string;
}

// Permission DTOs
export interface PermissionResponse {
  id: number;
  name: string;
}

export interface PermissionRequest {
  name: string;
}

// Todo DTOs
export interface TodoResponse {
  id: number;
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
  id: number;
  content: string;
  targetId: number;
  targetType: string; // EEntityTargetType
  authorId: number;
  markedForDeletion: boolean;
}

export interface CommentRequest {
  content: string;
  targetId: number;
  targetType: string; // EEntityTargetType
}

// Community DTOs
export interface CommunityResponse {
  id: number;
  name: string;
  description: string;
  createdBy: string;
  markedForDeletion: boolean;
}

export interface CommunityRequest {
  name: string;
  description: string;
}

// Post DTOs
export interface PostResponse {
  id: number;
  title: string;
  content: string;
  communityId: number;
  authorId: number;
  markedForDeletion: boolean;
}

export interface PostRequest {
  title: string;
  content: string;
  communityId: number;
}

// Vote DTOs
export interface VoteResponse {
  id: number;
  targetId: number;
  targetType: string;
  userId: number;
  upvote: boolean;
}

export interface VoteRequest {
  targetId: number;
  targetType: string;
  userId: number;
  upvote: boolean;
}