import api from './api';

const getTodayReport = async () => {
    const response = await api.get('/api/reports/today');
    console.log(response);
    return response.data;
};

const getMonthReport = async () => {
    const response = await api.get('/api/reports/month');
    return response.data;
};

const getYearReport = async () => {
    const response = await api.get('/api/reports/year');
    return response.data;
};

const downloadReport = async (period) => {
    // period: 1=Year, 2=Month, 3=Today
    const response = await api.get(`/api/export/download/${period}`, {
        responseType: 'blob'
    });

    // Create a download link for the blob
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `report_${new Date().getTime()}.xlsx`);
    document.body.appendChild(link);
    link.click();
    link.remove();
};

export default {
    getTodayReport,
    getMonthReport,
    getYearReport,
    downloadReport
};
