import axios, { AxiosInstance, AxiosResponse } from 'axios';

// Base API configuration - Force HTTP
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

// Ensure we're always using HTTP
const FORCE_HTTP_URL = API_BASE_URL.replace('https://', 'http://');

// Create axios instance
const apiClient: AxiosInstance = axios.create({
  baseURL: FORCE_HTTP_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000,
});

// Request interceptor to add auth token
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle token refresh
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = localStorage.getItem('refreshToken');
        if (refreshToken) {
          const response = await axios.post(`${API_BASE_URL}/auth/refresh`, {}, {
            headers: {
              Authorization: `Bearer ${refreshToken}`,
            },
          });

          const newToken = response.data.data.token;
          localStorage.setItem('token', newToken);
          localStorage.setItem('refreshToken', response.data.data.refreshToken);
          
          // Retry original request with new token
          originalRequest.headers.Authorization = `Bearer ${newToken}`;
          return apiClient(originalRequest);
        }
      } catch (refreshError) {
        // Refresh failed, redirect to login
        localStorage.removeItem('token');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('user');
        window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

// API Response interface matching backend
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
}

// Generic API methods
export const api = {
  get: async <T>(url: string): Promise<T> => {
    const response: AxiosResponse<ApiResponse<T>> = await apiClient.get(url);
    return response.data.data;
  },

  post: async <T, D = any>(url: string, data: D): Promise<T> => {
    const response: AxiosResponse<ApiResponse<T>> = await apiClient.post(url, data);
    return response.data.data;
  },

  put: async <T, D = any>(url: string, data: D): Promise<T> => {
    const response: AxiosResponse<ApiResponse<T>> = await apiClient.put(url, data);
    return response.data.data;
  },

  delete: async <T>(url: string): Promise<T> => {
    const response: AxiosResponse<ApiResponse<T>> = await apiClient.delete(url);
    return response.data.data;
  },

  patch: async <T, D = any>(url: string, data: D): Promise<T> => {
    const response: AxiosResponse<ApiResponse<T>> = await apiClient.patch(url, data);
    return response.data.data;
  },
};

export default apiClient;
