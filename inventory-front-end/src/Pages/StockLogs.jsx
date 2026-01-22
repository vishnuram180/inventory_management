import React, { useState, useEffect } from 'react';
import {
    Container,
    Typography,
    Box,
    Chip,
    CircularProgress,
    Alert
} from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import stockLogsService from '../services/stockLogsService';
import { isAdmin } from '../utils/auth';

const StockLogs = () => {
    const [logs, setLogs] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const isUserAdmin = isAdmin();

    const fetchLogs = async () => {
        setLoading(true);
        try {
            const data = await (isUserAdmin ? stockLogsService.getAllStockLogs() : stockLogsService.getMyStockLogs());
            setLogs(data);
        } catch (err) {
            console.error("Error fetching logs:", err);
            setError("Failed to load stock history.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchLogs();
    }, []);

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        {
            field: 'product_id',
            headerName: 'Product',
            flex: 1,
            valueGetter: (value) => value?.name || 'N/A'
        },
        {
            field: 'action',
            headerName: 'Action',
            width: 120,
            renderCell: (params) => (
                <Chip
                    label={params.value}
                    color={params.value === 'IN' ? 'success' : 'error'}
                    size="small"
                    sx={{ fontWeight: 'bold' }}
                />
            ),
        },
        { field: 'quantity', headerName: 'Quantity', width: 100 },
        {
            field: 'action_by',
            headerName: 'Performed By',
            width: 150,
            valueGetter: (value) => value?.username || 'N/A'
        },
        {
            field: 'action_date',
            headerName: 'Timestamp',
            width: 220,
            valueFormatter: (value) => {
                if (!value) return '';
                return new Date(value).toLocaleString();
            }
        },
    ];

    if (loading && logs.length === 0) {
        return (
            <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
                <CircularProgress />
            </Box>
        );
    }

    return (
        <Container maxWidth="lg">
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, mt: 4 }}>
                <Typography variant="h4" component="h1">
                    Stock Logs
                </Typography>
            </Box>

            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

            <div style={{ height: 600, width: '100%' }}>
                <DataGrid
                    rows={logs}
                    columns={columns}
                    loading={loading}
                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 10 },
                        },
                    }}
                    pageSizeOptions={[5, 10, 20]}
                    disableRowSelectionOnClick
                    sx={{
                        boxShadow: 2,
                        border: 2,
                        borderColor: 'primary.light',
                        '& .MuiDataGrid-cell:hover': {
                            color: 'primary.main',
                        },
                    }}
                />
            </div>
        </Container>
    );
};

export default StockLogs;
