import React, { useState, useEffect } from 'react';
import {
    Container,
    Typography,
    Grid,
    Paper,
    Box,
    Card,
    CardContent,
    CircularProgress,
    Divider
} from '@mui/material';
import {
    Inventory as InventoryIcon,
    Category as CategoryIcon,
    Warning as WarningIcon,
    People as PeopleIcon
} from '@mui/icons-material';
import { PieChart } from '@mui/x-charts/PieChart';
import { LineChart } from '@mui/x-charts/LineChart';
import api from '../services/api';

const Dashboard = () => {
    const [summary, setSummary] = useState(null);
    const [loading, setLoading] = useState(true);

    const fetchSummary = async () => {
        try {
            const response = await api.get('/api/dashboard/summary');
            setSummary(response.data);
        } catch (error) {
            console.error('Failed to fetch dashboard summary:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchSummary();
    }, []);

    if (loading) {
        return (
            <Box display="flex" justifyContent="center" alignItems="center" minHeight="80vh">
                <CircularProgress />
            </Box>
        );
    }

    const statCards = [
        { title: 'Total Products', value: summary?.totalProducts || 0, icon: <InventoryIcon color="primary" />, color: '#1976d2' },
        { title: 'Categories', value: summary?.totalCategories || 0, icon: <CategoryIcon color="secondary" />, color: '#9c27b0' },
        { title: 'Low Stock', value: summary?.lowStockCount || 0, icon: <WarningIcon color="error" />, color: '#d32f2f' },
        { title: 'Suppliers', value: summary?.totalSuppliers || 0, icon: <PeopleIcon color="info" />, color: '#0288d1' },
    ];

    const pieData = summary?.categoryDistribution?.map((item, index) => ({
        id: index,
        value: item.value,
        label: item.name
    })) || [];

    const trendData = summary?.movementTrends || [];
    const xLabels = trendData.map(t => t.date);
    const stockInData = trendData.map(t => t.stockIn);
    const stockOutData = trendData.map(t => t.stockOut);

    return (
        <Container maxWidth="xl" sx={{ mt: 2, mb: 2, height: 'calc(100vh - 100px)', display: 'flex', flexDirection: 'column' }}>
            <Box sx={{ mb: 2 }}>
                <Typography variant="h4" fontWeight="bold" gutterBottom>
                    Inventory Dashboard
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Real-time overview of your warehouse status.
                </Typography>
            </Box>

            <Grid container spacing={2} sx={{ flexGrow: 1 }}>
                {/* Metric Cards */}
                {statCards.map((card, index) => (
                    <Grid item xs={12} sm={6} md={3} key={index}>
                        <Card sx={{ height: '100%', boxShadow: 3, borderLeft: `6px solid ${card.color}` }}>
                            <CardContent sx={{ py: 1.5, '&:last-child': { pb: 1.5 } }}>
                                <Box display="flex" justifyContent="space-between" alignItems="center">
                                    <Box>
                                        <Typography color="text.secondary" variant="overline" fontWeight="bold" sx={{ lineHeight: 1 }}>{card.title}</Typography>
                                        <Typography variant="h4" fontWeight="bold" sx={{ mt: 0.5 }}>{card.value}</Typography>
                                    </Box>
                                    <Box sx={{ p: 1, bgcolor: `${card.color}15`, borderRadius: 2 }}>{card.icon}</Box>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}

                {/* Trends Chart */}
                <Grid item xs={12} lg={8} sx={{ height: 'calc(100% - 100px)' }}>
                    <Paper sx={{ p: 2, height: '100%', boxShadow: 3, display: 'flex', flexDirection: 'column' }}>
                        <Typography variant="h6" fontWeight="bold" gutterBottom>Weekly Stock Movement Trends</Typography>
                        <Divider sx={{ mb: 1 }} />
                        <Box sx={{ flexGrow: 1, minHeight: 0 }}>
                            <LineChart
                                series={[
                                    { data: stockInData, label: 'Stock In', color: '#4caf50', area: true },
                                    { data: stockOutData, label: 'Stock Out', color: '#ff9800', area: true },
                                ]}
                                xAxis={[{ scaleType: 'point', data: xLabels }]}
                                margin={{ left: 50, right: 30, top: 30, bottom: 30 }}
                                slotProps={{ legend: { direction: 'row', position: { vertical: 'top', horizontal: 'middle' }, padding: 0 } }}
                            />
                        </Box>
                    </Paper>
                </Grid>

                {/* Category Pie Chart */}
                <Grid item xs={12} lg={4} sx={{ height: 'calc(100% - 100px)' }}>
                    <Paper sx={{ p: 2, height: '100%', boxShadow: 3, display: 'flex', flexDirection: 'column' }}>
                        <Typography variant="h6" fontWeight="bold" gutterBottom>Stock Category Distribution</Typography>
                        <Divider sx={{ mb: 1 }} />
                        <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: 0 }}>
                            {pieData.length > 0 ? (
                                <PieChart
                                    series={[{
                                        data: pieData,
                                        innerRadius: '40%',
                                        outerRadius: '80%',
                                        paddingAngle: 5,
                                        cornerRadius: 5,
                                    }]}
                                    slotProps={{ legend: { hidden: true } }}
                                />
                            ) : (
                                <Typography color="text.secondary">No category data available</Typography>
                            )}
                        </Box>
                    </Paper>
                </Grid>
            </Grid>
        </Container>
    );
};

export default Dashboard;
