import React, { useState } from 'react';
import { styled, useTheme } from '@mui/material/styles';
import {
    Box,
    CssBaseline,
    Divider,
    IconButton,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Toolbar,
    Typography,
    Tooltip,
    Avatar
} from '@mui/material';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import {
    Menu as MenuIcon,
    ChevronLeft as ChevronLeftIcon,
    ChevronRight as ChevronRightIcon,
    Dashboard as DashboardIcon,
    Inventory as InventoryIcon,
    Category as CategoryIcon,
    People as PeopleIcon,
    Input as InputIcon,
    Output as OutputIcon,
    Receipt as ReceiptIcon,
    Assessment as AssessmentIcon,
    Logout as LogoutIcon,
    Store as StoreIcon
} from '@mui/icons-material';

import { useNavigate, useLocation } from 'react-router-dom';
import api from '../services/api';

const drawerWidth = 260;

// Mixins for transitions
const openedMixin = (theme) => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
    background: `linear-gradient(180deg, ${theme.palette.background.paper} 0%, ${theme.palette.background.default} 100%)`,
    borderRight: `1px solid ${theme.palette.divider}`,
});

const closedMixin = (theme) => ({
    transition: theme.transitions.create('width', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: `calc(${theme.spacing(7)} + 1px)`,
    [theme.breakpoints.up('sm')]: {
        width: `calc(${theme.spacing(8)} + 1px)`,
    },
    background: theme.palette.background.paper,
});

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
}));

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
    zIndex: theme.zIndex.drawer + 1,
    background: 'transparent',
    boxShadow: 'none',
    transition: theme.transitions.create(['width', 'margin', 'background'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
    ({ theme, open }) => ({
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap',
        boxSizing: 'border-box',
        ...(open && {
            ...openedMixin(theme),
            '& .MuiDrawer-paper': openedMixin(theme),
        }),
        ...(!open && {
            ...closedMixin(theme),
            '& .MuiDrawer-paper': closedMixin(theme),
        }),
    }),
);

import { isAdmin } from '../utils/auth';

const Navbar = ({ children }) => {
    const theme = useTheme();
    const [open, setOpen] = useState(true);
    const navigate = useNavigate();
    const location = useLocation();

    const isUserAdmin = isAdmin();

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    const handleLogout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('RefreshToken');
        delete api.defaults.headers.common['Authorization'];
        navigate('/login');
    };

    const allMenuItems = [
        { text: 'Dashboard', icon: <DashboardIcon />, path: '/' },
        { text: 'Categories', icon: <CategoryIcon />, path: '/categories', adminOnly: true },
        { text: 'Products', icon: <InventoryIcon />, path: '/products' },
        { text: 'Suppliers', icon: <PeopleIcon />, path: '/suppliers', adminOnly: true },
        { text: 'Stock In', icon: <InputIcon />, path: '/stock-in' },
        { text: 'Stock Out', icon: <OutputIcon />, path: '/stock-out' },
        { text: 'Stock Logs', icon: <ReceiptIcon />, path: '/stock-logs' },
        { text: 'Reports', icon: <AssessmentIcon />, path: '/reports', adminOnly: true },
    ];

    const menuItems = allMenuItems.filter(item => !item.adminOnly || isUserAdmin);

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />

            {/* Drawer */}
            <Drawer variant="permanent" open={open}>
                <DrawerHeader sx={{ justifyContent: open ? 'space-between' : 'center', px: 1 }}>
                    <Box sx={{ display: open ? 'flex' : 'none', alignItems: 'center', ml: 1, transition: '0.3s' }}>
                        <Avatar sx={{ bgcolor: theme.palette.primary.main, width: 40, height: 40, mr: 2 }}>
                            <StoreIcon />
                        </Avatar>
                        <Typography variant="h6" noWrap component="div" sx={{ fontWeight: 'bold', color: theme.palette.primary.main }}>
                            IMS
                        </Typography>
                    </Box>
                    <IconButton onClick={open ? handleDrawerClose : handleDrawerOpen}>
                        {open ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                    </IconButton>
                </DrawerHeader>
                <Divider />
                <List sx={{ mt: 2 }}>
                    {menuItems.map((item) => {
                        const isActive = location.pathname === item.path;
                        return (
                            <ListItem key={item.text} disablePadding sx={{ display: 'block', mb: 1 }}>
                                <Tooltip title={!open ? item.text : ""} placement="right">
                                    <ListItemButton
                                        onClick={() => navigate(item.path)}
                                        sx={{
                                            minHeight: 48,
                                            justifyContent: open ? 'initial' : 'center',
                                            px: 2.5,
                                            mx: 1,
                                            borderRadius: 2,
                                            background: isActive ? `linear-gradient(45deg, ${theme.palette.primary.main} 30%, ${theme.palette.primary.dark} 90%)` : 'transparent',
                                            boxShadow: isActive ? '0 3px 5px 2px rgba(33, 203, 243, .3)' : 'none',
                                            '&:hover': {
                                                bgcolor: isActive ? theme.palette.primary.dark : theme.palette.action.hover,
                                            },
                                        }}
                                    >
                                        <ListItemIcon
                                            sx={{
                                                minWidth: 0,
                                                mr: open ? 3 : 'auto',
                                                justifyContent: 'center',
                                                color: isActive ? '#fff' : theme.palette.text.secondary
                                            }}
                                        >
                                            {item.icon}
                                        </ListItemIcon>
                                        <ListItemText
                                            primary={item.text}
                                            sx={{
                                                opacity: open ? 1 : 0,
                                                color: isActive ? '#fff' : theme.palette.text.primary
                                            }}
                                        />
                                    </ListItemButton>
                                </Tooltip>
                            </ListItem>
                        )
                    })}
                </List>
                <Box sx={{ flexGrow: 1 }} />
                <Divider />
                <List>
                    <ListItem disablePadding sx={{ display: 'block', mb: 1 }}>
                        <Tooltip title={!open ? "Logout" : ""} placement="right">
                            <ListItemButton
                                onClick={handleLogout}
                                sx={{
                                    minHeight: 48,
                                    justifyContent: open ? 'initial' : 'center',
                                    px: 2.5,
                                    mx: 1,
                                    borderRadius: 2,
                                    '&:hover': { bgcolor: theme.palette.error.light, color: '#fff', '& .MuiListItemIcon-root': { color: '#fff' } }
                                }}
                            >
                                <ListItemIcon
                                    sx={{
                                        minWidth: 0,
                                        mr: open ? 3 : 'auto',
                                        justifyContent: 'center',
                                        color: theme.palette.error.main
                                    }}
                                >
                                    <LogoutIcon />
                                </ListItemIcon>
                                <ListItemText primary="Logout" sx={{ opacity: open ? 1 : 0, color: theme.palette.text.primary }} />
                            </ListItemButton>
                        </Tooltip>
                    </ListItem>
                </List>
            </Drawer>

            {/* Main Content */}
            <Box component="main" sx={{ flexGrow: 1, p: 3, pt: 5, maxWidth: '100%', overflowX: 'hidden' }}>
                {children}
            </Box>
        </Box>
    );
};

export default Navbar;
