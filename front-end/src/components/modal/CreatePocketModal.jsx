import { useState } from "react";
import PocketService from "../service/PocketService";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function CreatePocketModal({ isOpen, setIsOpen }) {
  const [formData, setFormData] = useState({
    type: "POCKET",
    currency: "",
    name: "",
    targetDate: "",
    targetAmount: "",
    autoDepositEnabled: false,
    autoDepositAmount: "",
  });
  const handleSubmitForm = () => {};

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  return (
    <>
      {isOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-surface-0 border border-surface-1 p-6 rounded w-fit">
            <div className="flex justify-between p-2">
              <p className="text-3xl text-text font-semibold">New pocket</p>
              <button
                className="ml-auto px-4 rounded-full bg-customred font-semibold text-text"
                onClick={setIsOpen}
              >
                x
              </button>
            </div>
            <div className="">
              <form className="block space-y-2" onSubmit={handleSubmitForm}>
                <ul className="space-y-2">
                  <li>
                    <input
                      className="p-2 rounded-xl bg-surface-1 focus:outline-none transition-all text-text placeholder:text-subtext placeholder:text-opacity-50"
                      type="text"
                      placeholder="Pocket name"
                      name="name"
                      value={formData.name}
                      onChange={handleInputChange}
                      required
                    />
                  </li>
                  <li>
                    <div className="p-2 bg-surface-1 rounded-xl ">
                      <input
                        className="bg-surface-1 focus:outline-none transition-all text-text placeholder:text-subtext placeholder:text-opacity-50"
                        type="number"
                        placeholder="Target amount"
                        name="targetAmount"
                        value={formData.targetAmount}
                        onChange={handleInputChange}
                        required
                      />

                      <select
                        className="bg-surface-1 focus:outline-none transition-all text-text"
                        name="currency"
                        value={formData.currency}
                        onChange={handleInputChange}
                      >
                        <option value="UAH">UAH</option>
                        <option value="USD">USD</option>
                        <option value="EUR">EUR</option>
                        <option value="JPY">JPY</option>
                        <option value="CHF">CHF</option>
                        <option value="PLN">PLN</option>
                      </select>
                    </div>
                  </li>
                  <li>
                    <p className="p-2 rounded-xl bg-surface-1 focus:outline-none transition-all text-subtext text-opacity-50">
                      Target date:
                      <span className="ml-4">
                        <input
                          className="text-subtext text-center bg-white bg-opacity-5 rounded-lg"
                          type="date"
                          name="targetDate"
                          value={formData.targetDate}
                          onChange={handleInputChange}
                          onClick={(e) => console.log(e.target.value)}
                          required
                        />
                      </span>
                    </p>
                  </li>
                  <li>
                    <p className="p-2 rounded-xl bg-surface-1 focus:outline-none transition-all text-subtext">
                      Enable auto deposit?
                      <span className="ml-4">
                        <input
                          className=""
                          type="checkbox"
                          name="autoDepositEnabled"
                          value={formData.autoDepositEnabled}
                          onChange={(e) =>
                            setFormData({
                              ...formData,
                              autoDepositEnabled: e.target.checked,
                            })
                          }
                        />
                      </span>
                    </p>
                  </li>
                  <li>
                    {formData.autoDepositEnabled && (
                      <input
                        className="p-2 rounded-xl bg-surface-1 focus:outline-none transition-all text-text placeholder:text-subtext placeholder:text-opacity-50"
                        type="number"
                        name="autoDepositAmount"
                        value={formData.autoDepositAmount}
                        onChange={handleInputChange}
                        placeholder="Auto deposit amount"
                      />
                    )}
                  </li>
                </ul>
                <input
                  className="py-2 px-4 text-center bg-green-1 rounded-full transition-all hover:bg-white"
                  type="submit"
                  value="Create Pocket"
                />
              </form>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
