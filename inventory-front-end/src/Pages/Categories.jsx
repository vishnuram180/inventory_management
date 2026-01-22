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
import { Add as AddIcon, Delete as DeleteIcon, ToggleOn as ToggleOnIcon, ToggleOff as ToggleOffIcon } from '@mui/icons-material';
import categoryService from '../services/categoryService';
import { isAdmin } from '../utils/auth';

const Categories = () => {
    const [categories, setCategories] = useState([]);
    const [open, setOpen] = useState(false);
    const [isEdit, setIsEdit] = useState(false);
    const [currentId, setCurrentId] = useState(null);
    const [formData, setFormData] = useState({
        name: '',
        status: true
    });
    const [loading, setLoading] = useState(false);

    const admin = isAdmin();

    const fetchCategories = async () => {
        try {
            const data = await categoryService.getAllCategories();
            // Ensure each row has an 'id' property for DataGrid
            const formattedData = data.map(item => ({
                ...item,
                id: item.categories_id
            }));
            setCategories(formattedData);
        } catch (error) {
            console.error('Failed to fetch categories:', error);
        }
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    const handleOpen = (category = null) => {
        if (category) {
            setIsEdit(true);
            setCurrentId(category.categories_id);
            setFormData({
                name: category.name,
                status: category.status ?? true
            });
        } else {
            setIsEdit(false);
            setCurrentId(null);
            setFormData({ name: '', status: true });
        }
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleSubmit = async () => {
        setLoading(true);
        try {
            const payload = {
                name: formData.name,
                status: formData.status
            };

            if (isEdit) {
                await categoryService.updateCategory(currentId, payload);
            } else {
                await categoryService.createCategory(payload);
            }
            fetchCategories();
            handleClose();
        } catch (error) {
            console.error('Operation failed:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this category?')) {
            try {
                await categoryService.deleteCategory(id);
                fetchCategories();
            } catch (error) {
                console.error('Delete failed:', error);
            }
        }
    };

    const handleToggleStatus = async (id) => {
        if (window.confirm('Are you sure you want to change the status of this category?')) {
            try {
                await categoryService.toggleCategoryStatus(id);
                fetchCategories();
            } catch (error) {
                console.error('Toggle failed:', error);
            }
        }
    };

    const columns = [
        { field: 'categories_id', headerName: 'ID', width: 90 },
        { field: 'name', headerName: 'Name', flex: 1 },
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
    ];

    if (admin) {
        columns.push({
            field: 'actions',
            headerName: 'Actions',
            width: 150,
            sortable: false,
            renderCell: (params) => (
                <Box>
                    <Tooltip title={params.row.status ? "Deactivate" : "Activate"}>
                        <IconButton onClick={() => handleToggleStatus(params.row.id)} color={params.row.status ? "warning" : "success"} size="small">
                            {params.row.status ? <ToggleOffIcon /> : <ToggleOnIcon />}
                        </IconButton>
                    </Tooltip>
                    <Tooltip title="Delete">
                        <IconButton onClick={() => handleDelete(params.row.id)} color="error" size="small">
                            <DeleteIcon />
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
                    Categories
                </Typography>
                {admin && (
                    <Button
                        variant="contained"
                        startIcon={<AddIcon />}
                        onClick={() => handleOpen()}
                    >
                        Add Category
                    </Button>
                )}
            </Box>

            <div style={{ height: 500, width: '100%' }}>
                <DataGrid
                    rows={categories}
                    columns={columns}
                    initialState={{
                        pagination: {
                            paginationModel: { page: 0, pageSize: 5 },
                        },
                    }}
                    pageSizeOptions={[5, 10]}
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

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>{isEdit ? 'Edit Category' : 'Add Category'}</DialogTitle>
                <DialogContent sx={{ width: 400 }}>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Category Name"
                        type="text"
                        fullWidth
                        variant="outlined"
                        value={formData.name}
                        onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                        sx={{ mb: 2 }}
                    />
                    <FormControl fullWidth>
                        <InputLabel id="status-label">Status</InputLabel>
                        <Select
                            labelId="status-label"
                            value={formData.status ?? true}
                            label="Status"
                            onChange={(e) => setFormData({ ...formData, status: e.target.value })}
                        >
                            <MenuItem value={true}>Active</MenuItem>
                            <MenuItem value={false}>Inactive</MenuItem>
                        </Select>
                    </FormControl>
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

export default Categories;
