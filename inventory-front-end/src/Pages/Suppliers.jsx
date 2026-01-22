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
    IconButton,
    Tooltip,
    FormControlLabel,
    Checkbox,
    Chip,
    Alert
} from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import { Add as AddIcon, Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import supplierService from '../services/supplierService';
import { isAdmin } from '../utils/auth';

const Suppliers = () => {
    const [suppliers, setSuppliers] = useState([]);
    const [open, setOpen] = useState(false);
    const [isEdit, setIsEdit] = useState(false);
    const [currentId, setCurrentId] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const [formData, setFormData] = useState({
        supplier_name: '',
        email: '',
        status: true
    });

    const admin = isAdmin();

    const fetchSuppliers = async () => {
        setLoading(true);
        try {
            const data = await supplierService.getSuppliers();
            // Ensure unique ID for DataGrid
            const formattedData = data.map(item => ({
                ...item,
                id: item.supplier_id
            }));
            setSuppliers(formattedData);
            setError(null);
        } catch (err) {
            console.error('Failed to fetch suppliers:', err);
            setError('Failed to load suppliers. You might not have permission.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (admin) {
            fetchSuppliers();
        }
    }, [admin]);

    const handleOpen = (supplier = null) => {
        if (supplier) {
            setIsEdit(true);
            setCurrentId(supplier.supplier_id);
            setFormData({
                supplier_name: supplier.supplier_name,
                email: supplier.email,
                status: supplier.status ?? true
            });
        } else {
            setIsEdit(false);
            setCurrentId(null);
            setFormData({
                supplier_name: '',
                email: '',
                status: true
            });
        }
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleSubmit = async () => {
        try {
            if (isEdit) {
                await supplierService.updateSupplier(currentId, formData);
            } else {
                await supplierService.addSupplier(formData);
            }
            fetchSuppliers();
            handleClose();
        } catch (err) {
            console.error('Operation failed:', err);
            alert('Operation failed. Please check the input or try again.');
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this supplier?')) {
            try {
                await supplierService.deleteSupplier(id);
                fetchSuppliers();
            } catch (err) {
                console.error('Delete failed:', err);
                alert('Failed to delete supplier.');
            }
        }
    };

    const columns = [
        { field: 'supplier_id', headerName: 'ID', width: 70 },
        { field: 'supplier_name', headerName: 'Name', flex: 1 },
        { field: 'email', headerName: 'Email', flex: 1 },
        {
            field: 'status',
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
        {
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
                    <Tooltip title="Delete">
                        <IconButton onClick={() => handleDelete(params.row.id)} color="error" size="small">
                            <DeleteIcon />
                        </IconButton>
                    </Tooltip>
                </Box>
            ),
        },
    ];

    if (!admin) {
        return (
            <Container sx={{ mt: 4 }}>
                <Alert severity="error">Access Denied: You must be an Admin to view this page.</Alert>
            </Container>
        );
    }

    return (
        <Container maxWidth="lg">
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, mt: 4 }}>
                <Typography variant="h4" component="h1">
                    Suppliers
                </Typography>
                <Button
                    variant="contained"
                    startIcon={<AddIcon />}
                    onClick={() => handleOpen()}
                >
                    Add Supplier
                </Button>
            </Box>

            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

            <div style={{ height: 600, width: '100%' }}>
                <DataGrid
                    rows={suppliers}
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

            <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
                <DialogTitle>{isEdit ? 'Edit Supplier' : 'Add Supplier'}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Supplier Name"
                        fullWidth
                        variant="outlined"
                        value={formData.supplier_name}
                        onChange={(e) => setFormData({ ...formData, supplier_name: e.target.value })}
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        margin="dense"
                        label="Email"
                        type="email"
                        fullWidth
                        variant="outlined"
                        value={formData.email}
                        onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                        sx={{ mb: 2 }}
                    />
                    <FormControlLabel
                        control={
                            <Checkbox
                                checked={formData.status}
                                onChange={(e) => setFormData({ ...formData, status: e.target.checked })}
                                color="primary"
                            />
                        }
                        label="Active"
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained">
                        {isEdit ? 'Update' : 'Add'}
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default Suppliers;
