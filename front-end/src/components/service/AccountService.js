import axios from "axios";

export default class AccountService {
  static BASE_URL = "http://localhost:8765/api/v1/account";

  static async getMyAccounts(token) {
    try {
      const response = await axios.get(`${this.BASE_URL}/get`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    } catch (err) {
      throw err;
    }
  }
}
