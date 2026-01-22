import api from './api';

const getAllStockOut = async () => {
    const response = await api.get('/api/stock_out/getall');
    return response.data;
};

const getMyStockOut = async () => {
    const response = await api.get('/api/stock_out/my_stock');
    return response.data;
};

const addStockOut = async (stockOutData) => {
    const response = await api.post('/api/stock_out/add', stockOutData);
    return response.data;
};

const updateStockOut = async (id, stockOutData) => {
    const response = await api.put(`/api/stock_out/update/${id}`, stockOutData);
    return response.data;
};

export default {
    getAllStockOut,
    getMyStockOut,
    addStockOut,
    updateStockOut
};
