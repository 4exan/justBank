import axios from "axios";

export default class AuthService {
  static BASE_URL = "http://localhost:8765";

  static async registration(userData, token) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/auth/registration`,
        userData,
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async login(username, password) {
    try {
      const response = await axios.post(`${AuthService.BASE_URL}/auth/login`, {
        username,
        password,
      });
      return response.data;
    } catch (err) {
      throw err;
    }
  }

  static async getMyProfile(token) {
    try {
      const response = await axios.get(`${AuthService.BASE_URL}/auth/profile`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  static async tokenValidation(token) {
    try {
      const response = await axios.post(
        `${AuthService.BASE_URL}/auth/validate`,
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
