import { createContext, useContext, useEffect, useState } from "react";
import AuthService from "../service/AuthService";
import { useNavigate } from "react-router-dom";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthencticated] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [payload, setPayload] = useState({
    token: "",
  });

  useEffect(() => {
    isTokenExpired();
  }, []);

  const isTokenExpired = async (token) => {
    const localToken = localStorage.getItem("token");
    setPayload(() => (payload.token = localToken));
    try {
      const response = await AuthService.tokenValidation(payload);
      console.log(response);
      if (response === "OK") {
        login();
      } else {
        logout();
      }
    } catch (e) {
      logout();
      throw e;
    }
  };

  const login = () => {
    setIsAuthencticated(true);
    setIsAdmin(AuthService.isAdmin());
  };

  const logout = () => {
    AuthService.logout();
    setIsAuthencticated(false);
    setIsAdmin(false);
    navigate("/home");
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, isAdmin, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
