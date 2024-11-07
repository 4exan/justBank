import { useState } from "react";
import TransactionService from "../service/TransactionService";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function CreateTransactionModal({
  isOpen,
  setIsOpen,
  accountNumber,
  accountCurrency,
}) {
  const [formData, setFormData] = useState({
    senderAccountNumber: accountNumber,
    receiverAccountNumber: "",
    amount: 0.0,
    currency: accountCurrency,
    transactionType: "PAYMENT",
    description: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmitForm = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem("token");
      console.log(formData);
      await TransactionService.createTransaction(token, formData);
      setFormData({
        senderAccountNumber: accountNumber,
        receiverAccountNumber: "",
        amount: 0.0,
        currency: accountCurrency,
        transactionType: "PAYMENT",
        description: "",
      });
      setIsOpen();
    } catch (e) {
      throw e;
    }
  };

  return (
    <>
      {isOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-base border border-surface-1 p-6 rounded w-fit">
            <div className="flex justify-between p-2">
              <p className="text-3xl text-text font-semibold">
                New transaction
              </p>
              <button
                className="ml-auto px-4 rounded-full bg-customred font-semibold text-text"
                onClick={setIsOpen}
              >
                x
              </button>
            </div>
            <div>
              <form className="block space-y-2" onSubmit={handleSubmitForm}>
                <input
                  className="p-2 rounded-xl bg-surface-1 focus:outline-none transition-all text-subtext"
                  type="text"
                  placeholder="Receiver account number"
                  name="receiverAccountNumber"
                  value={formData.receiverAccountNumber}
                  onChange={handleInputChange}
                />
                <br />
                <input
                  className="p-2 rounded-xl bg-surface-1 focus:outline-none transition-all text-subtext"
                  type="number"
                  placeholder="Amount"
                  name="amount"
                  value={formData.amount}
                  onChange={handleInputChange}
                />
                <br />
                <input
                  className="p-2 rounded-xl bg-surface-1 focus:outline-none transition-all text-subtext"
                  type="text"
                  placeholder="Description"
                  name="description"
                  value={formData.description}
                  onChange={handleInputChange}
                />
                <br />
                <input
                  className="py-2 px-4 text-center bg-green-1 rounded-full transition-all hover:bg-white"
                  type="submit"
                />
              </form>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
