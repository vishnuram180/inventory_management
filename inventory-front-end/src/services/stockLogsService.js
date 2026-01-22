import api from './api';

const getAllStockLogs = async () => {
    const response = await api.get('/api/stock_log/getall');
    return response.data;
};

const getMyStockLogs = async () => {
    const response = await api.get('/api/stock_log/my_stock');
    return response.data;
};

export default {
    getAllStockLogs,
    getMyStockLogs
};
