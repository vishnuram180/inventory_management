import React, { useState, useEffect } from 'react';
import {
    Container,
    Typography,
    Button,
    Box,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    MenuItem,
    Alert,
    CircularProgress,
    IconButton,
    Tooltip
} from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { Add as AddIcon, Edit as EditIcon } from '@mui/icons-material';
import stockOutService from '../services/stockOutService';
import productService from '../services/productService';
import { isAdmin, getUserRole } from '../utils/auth';

const StockOut = () => {
    const [stocks, setStocks] = useState([]);
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [openDialog, setOpenDialog] = useState(false);
    const [editMode, setEditMode] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [formData, setFormData] = useState({
        id: null,
        product_id: { product_id: '' },
        quantity: '',
        issued_to: '',
        issued_by: { id: '' }
    });

    const isUserAdmin = isAdmin();
    const currentUser = getUserRole();

    const fetchData = async () => {
        setLoading(true);
        try {
            const [stockData, productData] = await Promise.all([
                isUserAdmin ? stockOutService.getAllStockOut() : stockOutService.getMyStockOut(),
                productService.getAllProducts(0, 1000)
            ]);

            const productList = productData.content || productData;

            setStocks(stockData);
            setProducts(productList);
        } catch (err) {
            console.error("Error fetching data:", err);
            setError("Failed to load data. Please check your permissions.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleOpenDialog = (stock = null) => {
        if (stock) {
            setEditMode(true);
            setFormData({
                id: stock.id,
                product_id: { product_id: stock.product_id.product_id },
                quantity: stock.quantity,
                issued_to: stock.issued_to,
                issued_by: { id: stock.issued_by.id }
            });
        } else {
            setEditMode(false);
            setFormData({
                id: null,
                product_id: { product_id: '' },
                quantity: '',
                issued_to: '',
                issued_by: { id: currentUser?.ID || '' }
            });
        }
        setOpenDialog(true);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setError(null);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'product_id') {
            setFormData(prev => ({
                ...prev,
                [name]: { [name]: value }
            }));
        } else {
            setFormData(prev => ({
                ...prev,
                [name]: value
            }));
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        try {
            if (editMode) {
                await stockOutService.updateStockOut(formData.id, formData);
                setSuccess("Stock entry updated successfully!");
            } else {
                await stockOutService.addStockOut(formData);
                setSuccess("Stock issuance recorded successfully!");
            }
            fetchData();
            handleCloseDialog();
            setTimeout(() => setSuccess(null), 3000);
        } catch (err) {
            console.error("Error saving stock info:", err);
            setError("Failed to save stock record. Please check stock levels.");
        }
    };

    const columns = [
        { field: 'id', headerName: 'ID', width: 70 },
        {
            field: 'product_id',
            headerName: 'Product',
            flex: 1,
            valueGetter: (value) => value?.name || 'N/A'
        },
        { field: 'issued_to', headerName: 'Issued To', flex: 1 },
        { field: 'quantity', headerName: 'Quantity', width: 100 },
        {
            field: 'issued_by',
            headerName: 'Issued By',
            width: 150,
            valueGetter: (value) => value?.username || 'N/A'
        },
        { field: 'issued_date', headerName: 'Date', width: 150 },
    ];

    if (isUserAdmin) {
        columns.push({
            field: 'actions',
            headerName: 'Actions',
            width: 100,
            renderCell: (params) => (
                <IconButton onClick={() => handleOpenDialog(params.row)} color="primary" size="small">
                    <EditIcon />
                </IconButton>
            ),
        });
    }

    if (loading && stocks.length === 0) {
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
                    Stock Out
                </Typography>
                <Button
                    variant="contained"
                    startIcon={<AddIcon />}
                    onClick={() => handleOpenDialog()}
                >
                    Add Stock Out
                </Button>
            </Box>

            {success && <Alert severity="success" sx={{ mb: 2 }}>{success}</Alert>}

            <div style={{ height: 600, width: '100%' }}>
                <DataGrid
                    rows={stocks}
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

            <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
                <form onSubmit={handleSubmit}>
                    <DialogTitle>
                        {editMode ? 'Edit Stock Record' : 'Record New Stock Issuance'}
                    </DialogTitle>
                    <DialogContent dividers>
                        {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

                        <TextField
                            select
                            fullWidth
                            label="Product"
                            name="product_id"
                            value={formData.product_id.product_id}
                            onChange={handleChange}
                            required
                            margin="normal"
                            variant="outlined"
                        >
                            {products.map((p) => (
                                <MenuItem key={p.product_id} value={p.product_id}>
                                    {p.name} (Current Stock: {p.current_stock})
                                </MenuItem>
                            ))}
                        </TextField>

                        <TextField
                            fullWidth
                            label="Issued To"
                            name="issued_to"
                            value={formData.issued_to}
                            onChange={handleChange}
                            required
                            margin="normal"
                            variant="outlined"
                            placeholder="Customer or Department name"
                        />

                        <TextField
                            fullWidth
                            label="Quantity"
                            name="quantity"
                            type="number"
                            value={formData.quantity}
                            onChange={handleChange}
                            required
                            margin="normal"
                            variant="outlined"
                            inputProps={{ min: 1 }}
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCloseDialog}>Cancel</Button>
                        <Button type="submit" variant="contained" color="primary">
                            {editMode ? 'Update' : 'Confirm Issuance'}
                        </Button>
                    </DialogActions>
                </form>
            </Dialog>
        </Container>
    );
};

export default StockOut;
