import api from './api';

const supplierService = {
    getSuppliers: async () => {
        const response = await api.get('/api/supplier/get');
        return response.data;
    },

    addSupplier: async (supplier) => {
        const response = await api.post('/api/supplier/add', supplier);
        return response.data;
    },

    updateSupplier: async (id, supplier) => {
        const response = await api.put(`/api/supplier/update/${id}`, supplier);
        return response.data;
    },

    deleteSupplier: async (id) => {
        await api.delete(`/api/supplier/delete/${id}`);
    }
};

export default supplierService;
