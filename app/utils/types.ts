export interface DartApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

export interface User {
  username: string;
  email: string;
}

export interface LoginResponse {
  token: string;
}