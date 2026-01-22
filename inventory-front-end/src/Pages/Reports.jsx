import React, { useState, useEffect } from 'react';
import {
    Container,
    Typography,
    Button,
    Box,
    Tabs,
    Tab,
    Alert,
    CircularProgress,
    Stack
} from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { Download as DownloadIcon } from '@mui/icons-material';
import reportService from '../services/reportService';
import { isAdmin } from '../utils/auth';

const Reports = () => {
    const [reports, setReports] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [period, setPeriod] = useState(3); // Default: 3 = Today
    const [tabValue, setTabValue] = useState(0);

    const isUserAdmin = isAdmin();

    const fetchReportData = async (currentPeriod) => {
        setLoading(true);
        setError(null);
        try {
            let data;
            if (currentPeriod === 3) data = await reportService.getTodayReport();
            else if (currentPeriod === 2) data = await reportService.getMonthReport();
            else if (currentPeriod === 1) data = await reportService.getYearReport();

            // Add unique ID for DataGrid
            const formattedData = data.map((item, index) => ({
                ...item,
                product_Name: item.product_Name || 'Unknown Product',
                id: item.product_Name ? `${item.product_Name}-${item.date}-${index}` : `unknown-${index}`
            }));

            setReports(formattedData);
        } catch (err) {
            console.error("Error fetching report:", err);
            setError("Failed to load report data. Ensure you have Admin privileges.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (isUserAdmin) {
            fetchReportData(period);
        }
    }, [period, isUserAdmin]);

    const handleTabChange = (event, newValue) => {
        setTabValue(newValue);
        const periods = [3, 2, 1]; // Today, Month, Year
        setPeriod(periods[newValue]);
    };

    const handleExport = async () => {
        try {
            await reportService.downloadReport(period);
        } catch (err) {
            console.error("Export failed:", err);
            alert("Failed to download report. Please try again.");
        }
    };

    const columns = [
        { field: 'date', headerName: 'Date', width: 130 },
        { field: 'product_Name', headerName: 'Product Name', flex: 1 },
        {
            field: 'stock_in',
            headerName: 'Stock In',
            width: 130,
            type: 'number',
            valueGetter: (value) => value || 0
        },
        {
            field: 'stock_out',
            headerName: 'Stock Out',
            width: 130,
            type: 'number',
            valueGetter: (value) => value || 0
        },
        {
            field: 'available_stock',
            headerName: 'Available Stock',
            width: 150,
            type: 'number',
            valueGetter: (value) => value || 0,
            renderCell: (params) => (
                <Typography sx={{ fontWeight: 'bold', color: params.value < 10 ? 'error.main' : 'inherit' }}>
                    {params.value}
                </Typography>
            )
        },
    ];

    if (!isUserAdmin) {
        return (
            <Container sx={{ mt: 4 }}>
                <Alert severity="error">Access Denied: You must be an Admin to view reports.</Alert>
            </Container>
        );
    }

    return (
        <Container maxWidth="lg">
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, mt: 4 }}>
                <Typography variant="h4" component="h1">
                    Inventory Reports
                </Typography>
                <Button
                    variant="contained"
                    color="success"
                    startIcon={<DownloadIcon />}
                    onClick={handleExport}
                    disabled={loading || reports.length === 0}
                >
                    Export to Excel
                </Button>
            </Box>

            <Box sx={{ borderBottom: 1, borderColor: 'divider', mb: 3 }}>
                <Tabs value={tabValue} onChange={handleTabChange} aria-label="report periods">
                    <Tab label="Today" />
                    <Tab label="This Month" />
                    <Tab label="This Year" />
                </Tabs>
            </Box>

            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

            <div style={{ height: 600, width: '100%' }}>
                <DataGrid
                    rows={reports}
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
                        '& .MuiDataGrid-columnHeaders': {
                            backgroundColor: 'action.hover',
                        }
                    }}
                />
            </div>
        </Container>
    );
};

export default Reports;
