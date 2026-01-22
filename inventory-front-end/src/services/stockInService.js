import api from './api';

const getAllStockIn = async () => {
    const response = await api.get('/api/stock_in/getall');
    return response.data;
};

const getMyStockIn = async () => {
    const response = await api.get('/api/stock_in/my_stock');
    return response.data;
};

const addStockIn = async (stockInData) => {
    const response = await api.post('/api/stock_in/add', stockInData);
    return response.data;
};

const updateStockIn = async (id, stockInData) => {
    const response = await api.put(`/api/stock_in/update/${id}`, stockInData);
    return response.data;
};

export default {
    getAllStockIn,
    getMyStockIn,
    addStockIn,
    updateStockIn
};
