import axios from "axios";

export default class PocketService {
  static BASE_URL = "http://localhost:8765/api/v1/account";

  static async getMyPockets(token) {
    try {
      const response = await axios.get(`${this.BASE_URL}/pockets`, {
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
