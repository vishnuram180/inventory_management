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
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    IconButton,
    Tooltip,
    Chip
} from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { Add as AddIcon, Edit as EditIcon, ToggleOn as ToggleOnIcon, ToggleOff as ToggleOffIcon } from '@mui/icons-material';
import productService from '../services/productService';
import categoryService from '../services/categoryService';
import { isAdmin } from '../utils/auth';

const Products = () => {
    const [products, setProducts] = useState([]);
    const [categories, setCategories] = useState([]);
    const [open, setOpen] = useState(false);
    const [isEdit, setIsEdit] = useState(false);
    const [currentId, setCurrentId] = useState(null);
    const [paginationModel, setPaginationModel] = useState({
        page: 0,
        pageSize: 5,
    });
    const [rowCount, setRowCount] = useState(0);

    const [formData, setFormData] = useState({
        name: '',
        price: '',
        current_stock: '',
        reorder_stock: '',
        category_id: ''
    });
    const [loading, setLoading] = useState(false);

    const admin = isAdmin();
    console.log("admin", admin);

    const fetchData = async () => {
        setLoading(true);
        try {
            const [productsData, categoriesData] = await Promise.all([
                productService.getAllProducts(paginationModel.page, paginationModel.pageSize),
                categoryService.getAllCategories()
            ]);

            const formattedProducts = productsData.content.map(item => ({
                ...item,
                id: item.product_id,
                categoryName: item.categories_id ? item.categories_id.name : 'N/A'
            }));

            setProducts(formattedProducts);
            setRowCount(productsData.totalElements);
            setCategories(categoriesData);
        } catch (error) {
            console.error('Failed to fetch data:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, [paginationModel]);

    const handleOpen = (product = null) => {
        if (product) {
            setIsEdit(true);
            setCurrentId(product.product_id);
            setFormData({
                name: product.name,
                price: product.price,
                current_stock: product.current_stock,
                reorder_stock: product.reorder_stock,
                category_id: product.categories_id ? product.categories_id.categories_id : ''
            });
        } else {
            setIsEdit(false);
            setCurrentId(null);
            setFormData({
                name: '',
                price: '',
                current_stock: '',
                reorder_stock: '',
                category_id: ''
            });
        }
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleSubmit = async () => {
        setLoading(true);
        try {
            // Backend expects categories_id as an object or id based on entity
            // Based on typical spring boot mapping with entity param:
            const payload = {
                name: formData.name,
                price: parseFloat(formData.price),
                current_stock: parseInt(formData.current_stock),
                reorder_stock: parseInt(formData.reorder_stock),
                categories_id: { categories_id: formData.category_id } // Sending object reference
            };

            if (isEdit) {
                await productService.updateProduct(currentId, payload);
            } else {
                await productService.createProduct(payload);
            }
            fetchData();
            handleClose();
        } catch (error) {
            console.error('Operation failed:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleToggleStatus = async (id) => {
        if (window.confirm('Are you sure you want to change the status of this product?')) {
            try {
                await productService.toggleProductStatus(id);
                fetchData();
            } catch (error) {
                console.error('Toggle failed:', error);
            }
        }
    };

    const columns = [
        { field: 'product_id', headerName: 'ID', width: 70 },
        { field: 'name', headerName: 'Name', flex: 1 },
        { field: 'categoryName', headerName: 'Category', width: 150 },
        {
            field: 'price',
            headerName: 'Price',
            width: 100,
            valueFormatter: (value) => {
                if (value == null) {
                    return '';
                }
                return `₹${parseFloat(value).toFixed(2)}`;
            },
        },
        { field: 'current_stock', headerName: 'Stock', width: 100, filterable: false },
        { field: 'reorder_stock', headerName: 'Reorder Level', width: 120 },
        {
            field: 'is_active',
            headerName: 'Status',
            width: 120,
            renderCell: (params) => (
                <Chip
                    label={params.value ? 'Active' : 'Inactive'}
                    color={params.value ? 'success' : 'default'}
                    size="small"
                />
            ),
        },
    ];

    if (admin) {

        columns.push({
            field: 'actions',
            headerName: 'Actions',
            width: 150,
            renderCell: (params) => (
                <Box>
                    <Tooltip title="Edit">
                        <IconButton onClick={() => handleOpen(params.row)} color="primary" size="small">
                            <EditIcon />
                        </IconButton>
                    </Tooltip>
                    <Tooltip title={params.row.is_active ? "Deactivate" : "Activate"}>
                        <IconButton onClick={() => handleToggleStatus(params.row.id)} color={params.row.is_active ? "warning" : "success"} size="small">
                            {params.row.is_active ? <ToggleOffIcon /> : <ToggleOnIcon />}
                        </IconButton>
                    </Tooltip>
                </Box>
            ),
        });
    }

    return (
        <Container maxWidth="lg">
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
                <Typography variant="h4" component="h1">
                    Products
                </Typography>
                {admin && (
                    <Button
                        variant="contained"
                        startIcon={<AddIcon />}
                        onClick={() => handleOpen()}
                    >
                        Add Product
                    </Button>
                )}
            </Box>

            <div style={{ height: 600, width: '100%' }}>
                <DataGrid
                    rows={products}
                    columns={columns}
                    rowCount={rowCount}
                    loading={loading}
                    pageSizeOptions={[5, 10, 20]}
                    paginationModel={paginationModel}
                    paginationMode="server"
                    onPaginationModelChange={setPaginationModel}
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

            <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
                <DialogTitle>{isEdit ? 'Edit Product' : 'Add Product'}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Product Name"
                        fullWidth
                        variant="outlined"
                        value={formData.name}
                        onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                        sx={{ mb: 2 }}
                    />
                    <FormControl fullWidth sx={{ mb: 2 }}>
                        <InputLabel id="category-label">Category</InputLabel>
                        <Select
                            labelId="category-label"
                            value={formData.category_id}
                            label="Category"
                            onChange={(e) => setFormData({ ...formData, category_id: e.target.value })}
                        >
                            {categories.map((cat) => (
                                <MenuItem key={cat.categories_id} value={cat.categories_id}>
                                    {cat.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Box sx={{ display: 'flex', gap: 2 }}>
                        <TextField
                            margin="dense"
                            label="Price"
                            type="number"
                            fullWidth
                            variant="outlined"
                            value={formData.price}
                            onChange={(e) => setFormData({ ...formData, price: e.target.value })}
                            sx={{ mb: 2 }}
                        />
                        <TextField
                            margin="dense"
                            label="Current Stock"
                            type="number"
                            fullWidth
                            variant="outlined"
                            value={formData.current_stock}
                            onChange={(e) => setFormData({ ...formData, current_stock: e.target.value })}
                            sx={{ mb: 2 }}
                        />
                    </Box>
                    <TextField
                        margin="dense"
                        label="Reorder Level"
                        type="number"
                        fullWidth
                        variant="outlined"
                        value={formData.reorder_stock}
                        onChange={(e) => setFormData({ ...formData, reorder_stock: e.target.value })}
                        sx={{ mb: 2 }}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained" disabled={loading}>
                        {isEdit ? 'Update' : 'Add'}
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default Products;
