import axios from "axios";

export default class AuthService {
  static BASE_URL = "http://localhost:8765/api/v1/auth";

  static async registration(userData) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/registration`,
        userData,
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async login(phone, password) {
    try {
      const response = await axios.post(`${AuthService.BASE_URL}/login`, {
        phone,
        password,
      });
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async tokenValidation(token) {
    try {
      const response = await axios.post(
        `${AuthService.BASE_URL}/validate`,
        token,
      );
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  /**AUTHENTICATION CHECKER */

  static logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
  }

  static isAuthenticated() {
    const token = localStorage.getItem("token");
    return !!token;
  }

  static isAdmin() {
    const role = localStorage.getItem("role");
    return role === "ADMIN";
  }

  static isUser() {
    const role = localStorage.getItem("role");
    return role === "USER";
  }

  static adminOnly() {
    return this.isAuthenticated() && this.isAdmin();
  }
}
