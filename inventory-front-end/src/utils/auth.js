import { jwtDecode } from 'jwt-decode';

export const getUserRole = () => {
    const token = localStorage.getItem('accessToken');
    if (!token) return null;
    try {
        const decoded = jwtDecode(token);
        return decoded;
    } catch (error) {
        return null;
    }
};

export const isAdmin = () => {
    const roleData = getUserRole();
    if (!roleData || !roleData.Roles) return false;

    // Check if roles is an array and contains ADMIN
    if (Array.isArray(roleData.Roles)) {
        return roleData.Roles.includes('ADMIN');
    }

    // Fallback for string roles
    return roleData.Roles === 'ADMIN';
};
