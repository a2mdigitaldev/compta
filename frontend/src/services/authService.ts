import { api } from './api';

// Authentication interfaces
export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  roleName: string;
}

export interface AuthResponse {
  token: string;
  refreshToken: string;
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
}

class AuthService {
  private authToken: string | null = null;

  setAuthToken(token: string | null) {
    this.authToken = token;
  }

  async login(email: string, password: string): Promise<AuthResponse> {
    try {
      const response = await api.post<AuthResponse, LoginRequest>('/auth/login', {
        email,
        password,
      });
      return response;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Login failed');
    }
  }

  async register(userData: RegisterRequest): Promise<AuthResponse> {
    try {
      const response = await api.post<AuthResponse, RegisterRequest>('/auth/register', userData);
      return response;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Registration failed');
    }
  }

  async logout(token: string): Promise<void> {
    try {
      // The token will be automatically added by the interceptor
      await api.post('/auth/logout', {});
    } catch (error: any) {
      console.error('Logout error:', error);
      // Don't throw error for logout, just log it
    }
  }

  async refreshToken(refreshToken: string): Promise<AuthResponse> {
    try {
      // Temporarily set token for this request
      const originalToken = localStorage.getItem('token');
      localStorage.setItem('token', refreshToken);
      const response = await api.post<AuthResponse>('/auth/refresh', {});
      // Restore original token
      if (originalToken) {
        localStorage.setItem('token', originalToken);
      }
      return response;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Token refresh failed');
    }
  }

  async validateToken(token: string): Promise<boolean> {
    try {
      const response = await api.get<boolean>('/auth/validate');
      return response;
    } catch (error) {
      return false;
    }
  }

  async getCurrentUser(token: string): Promise<AuthResponse> {
    try {
      const response = await api.get<AuthResponse>('/auth/me');
      return response;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to get user info');
    }
  }

  async changePassword(currentPassword: string, newPassword: string): Promise<void> {
    try {
      await api.post('/auth/change-password', {
        currentPassword,
        newPassword,
      });
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Password change failed');
    }
  }

  getAuthToken(): string | null {
    return this.authToken || localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return !!this.getAuthToken();
  }
}

export const authService = new AuthService();
