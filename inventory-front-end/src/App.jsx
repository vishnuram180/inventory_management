import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './Pages/Login.jsx';
import Signup from './Pages/Signup.jsx';
import Dashboard from './Pages/Dashboard.jsx';
import Categories from './Pages/Categories.jsx';
import Products from './Pages/Products.jsx';
import Suppliers from './Pages/Suppliers.jsx';
import StockIn from './Pages/StockIn.jsx';
import StockOut from './Pages/StockOut.jsx';
import StockLogs from './Pages/StockLogs.jsx';
import Reports from './Pages/Reports.jsx';
import Navbar from './components/Navbar.jsx';

const PrivateRoute = ({ children }) => {
  const token = localStorage.getItem('accessToken');
  return token ? <Navbar>{children}</Navbar> : <Navigate to="/login" />;
};

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />

        {/* Protected Routes */}
        <Route path="/" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/categories" element={<PrivateRoute><Categories /></PrivateRoute>} />
        <Route path="/products" element={<PrivateRoute><Products /></PrivateRoute>} />
        <Route path="/suppliers" element={<PrivateRoute><Suppliers /></PrivateRoute>} />
        <Route path="/stock-in" element={<PrivateRoute><StockIn /></PrivateRoute>} />
        <Route path="/stock-out" element={<PrivateRoute><StockOut /></PrivateRoute>} />
        <Route path="/stock-logs" element={<PrivateRoute><StockLogs /></PrivateRoute>} />
        <Route path="/reports" element={<PrivateRoute><Reports /></PrivateRoute>} />
      </Routes>
    </Router>
  );
};

export default App;