import api from './api';

const productService = {
    getAllProducts: async (page = 0, size = 5) => {
        const response = await api.get(`/api/product/get?page=${page}&size=${size}`);
        console.log(response.data);
        return response.data;
    },

    getProductById: async (id) => {
        const response = await api.get(`/api/product/getbyid/${id}`);
        return response.data;
    },

    createProduct: async (productData) => {
        const response = await api.post('/api/product/add', productData);
        return response.data;
    },

    updateProduct: async (id, productData) => {
        const response = await api.put(`/api/product/update/${id}`, productData);
        return response.data;
    },

    toggleProductStatus: async (id) => {
        const response = await api.put(`/api/product/toggle/${id}`);
        return response.data;
    }
};

export default productService;
