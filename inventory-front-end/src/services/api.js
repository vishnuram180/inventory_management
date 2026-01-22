import axios from 'axios';
import BASE_URL from '../api/base_api';

const api = axios.create({
    baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add a request interceptor to include the Auth Token
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Add a response interceptor to handle Token Refresh
api.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const originalRequest = error.config;

        if (error.response && error.response.status === 401 && !originalRequest._retry) {
            // Check if the error came from the login endpoint itself
            if (originalRequest.url.includes('/auth/login')) {
                return Promise.reject(error);
            }
            originalRequest._retry = true;

            try {
                const refreshToken = localStorage.getItem('RefreshToken');

                if (!refreshToken) {
                    // No refresh token, logout
                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('RefreshToken');
                    delete api.defaults.headers.common['Authorization'];
                    window.location.href = '/login';
                    return Promise.reject(error);
                }

                // Call refresh token endpoint (using axios directly to avoid interceptor loop)
                const response = await axios.post(`${BASE_URL}/auth/refresh`, {
                    Refresh_Token: refreshToken
                });

                if (response.status === 200) {
                    const { accessToken, RefreshToken: newRefreshToken } = response.data;

                    localStorage.setItem('accessToken', accessToken);
                    // Update refresh token if returned (optional but good practice)
                    if (newRefreshToken) {
                        localStorage.setItem('RefreshToken', newRefreshToken);
                    }

                    // Update header for original request
                    originalRequest.headers.Authorization = `Bearer ${accessToken}`;

                    // Update default header for future requests
                    api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

                    return api(originalRequest);
                }
            } catch (refreshError) {
                console.error("Token refresh failed:", refreshError);
                // Logout on refresh failure
                localStorage.removeItem('accessToken');
                localStorage.removeItem('RefreshToken');
                delete api.defaults.headers.common['Authorization'];
                window.location.href = '/login';
            }
        }
        return Promise.reject(error);
    }
);

export default api;
