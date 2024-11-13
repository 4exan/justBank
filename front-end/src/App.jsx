import "./App.css";
import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "./components/context/AuthContext";
import Navbar from "./components/common/Navbar";
import HomePage from "./components/page/HomePage";
import ErrorPage from "./components/page/ErrorPage";
import Dashboard from "./components/page/DashboardPage";
import Pocket from "./components/page/PocketPage";
import PremiumPage from "./components/page/PremiumPage";

function App() {
  const { isAuthenticated, isAdmin } = useAuth();
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            <Route exact path="/" element={<HomePage />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/pocket" element={<Pocket />} />
            <Route path="/premium" element={<PremiumPage />} />
            {isAuthenticated && <>{/* Routes for authenticated users*/}</>}
            {/* Check if user is authenticated and admin before rendering admin-only routes */}
            {isAdmin && <>{/* Routes for ADMIN roles*/}</>}
            <Route path="*" element={<ErrorPage />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
