import api from './api';

const categoryService = {
    getAllCategories: async () => {
        const response = await api.get('/api/categories/getall');
        return response.data;
    },

    createCategory: async (categoryData) => {
        const response = await api.post('/api/categories/add', categoryData);
        return response.data;
    },

    updateCategory: async (id, categoryData) => {
        const response = await api.put(`/api/categories/update/${id}`, categoryData);
        return response.data;
    },

    deleteCategory: async (id) => {
        await api.delete(`/api/categories/del/${id}`);
    },

    toggleCategoryStatus: async (id) => {
        const response = await api.put(`/api/categories/toggle/${id}`);
        return response.data;
    }
};

export default categoryService;
