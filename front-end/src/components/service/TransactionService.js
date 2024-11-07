import axios from "axios";

export default class TransactionService {
  static BASE_URL = "http://localhost:8765/api/v1/transaction";

  static async getAllTransaction(token, accountNumber) {
    try {
      const response = await axios.get(
        `${this.BASE_URL}/get-all/${accountNumber}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data;
    } catch (err) {
      throw err;
    }
  }
}
