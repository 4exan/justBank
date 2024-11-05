import React from "react";
import { useState, useEffect } from "react";
import AccountService from "../service/AccountService";

export default function Dashboard() {
  const [loading, setLoading] = useState(true);
  const [accounts, setAccounts] = useState([]);
  const [activeAccount, setActiveAccount] = useState({
    accountId: 1,
    userId: 1,
    type: "CHECKING",
    currency: "UAH",
    balance: 0.0,
    createdAt: "2024-10-31T10:49:39.530+00:00",
    updatedAt: "2024-10-31T10:49:39.530+00:00",
    status: "ACTIVE",
    active: false,
    twoFactorEnabled: false,
    number: "CHK95197252",
    overdraftLimit: 5000.0,
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const accountData = await AccountService.getMyAccounts(token);
      if (accountData?.accountList) {
        setAccounts(() => accountData.accountList);
        setActiveAccount(() => accountData.accountList[0]);
      } else {
        console.log("Empty data!");
      }
    } catch (e) {
      throw e;
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const selectedValue = e.target.value;
    const selectedAccount = accounts.find(
      (account) => account.number === selectedValue,
    );
    setActiveAccount(selectedAccount);
  };

  if (!loading) {
    return (
      <>
        <div className="p-4">
          <div className="">
            <select
              className="p-2 ml-2 bg-surface-1 rounded-xl text-green-1"
              onChange={handleChange}
            >
              {accounts.map((account) => (
                <option
                  key={account.accountId}
                  value={account.number}
                >{`${account.type} ${account.currency} ${account.number}`}</option>
              ))}
            </select>
          </div>
          <div className="flex">
            {/* LEFT SEGMENT */}
            <div className="w-1/3 p-2">
              <div className=" p-2 bg-surface-0 rounded-3xl">
                <div className="p-2 bg-white bg-opacity-5 rounded-2xl">
                  <p className="text-subtext font-light text-lg">Balance:</p>
                  <h1 className="text-text text-6xl text-opacity-80 font-medium">
                    {`${activeAccount.balance} ${activeAccount.currency}`}
                  </h1>
                </div>
                <div className="mt-4 p-2 bg-white bg-opacity-5 rounded-2xl">
                  <h1 className="font-light text-xl text-subtext">
                    Monthly total:
                  </h1>
                  <div className="p-2">
                    <div className="py-2 flex justify-between text-subtext border-b border-gray-500">
                      <p className="text-xl inline">Income:</p>
                      <p className="text-3xl inline ml-auto">TODO</p>
                    </div>
                    <div className="py-2 flex justify-between text-subtext">
                      <p className="text-xl inline">Spending:</p>
                      <p className="text-3xl inline ml-auto">TODO</p>
                    </div>
                  </div>
                </div>
                <div className="bg-surface-0 rounded-xl"></div>
              </div>
            </div>
            {/* MIDDLE SEGMENT */}
            <div className="w-1/3 p-2">
              <div className="bg-surface-0 rounded-3xl p-2">
                <p className="text-subtext text-3xl font-semibold border-b-2 border-green-1">
                  Account information
                </p>
                <p className="text-subtext text-lg font-semibold mt-2">
                  Account number:{" "}
                  <span className="font-normal">{activeAccount.number}</span>
                </p>
                <p className="text-subtext text-lg font-semibold">
                  Created at:{" "}
                  <span className="font-normal">{activeAccount.createdAt}</span>
                </p>
                <p className="text-subtext text-lg font-semibold">
                  Last edit at:{" "}
                  <span className="font-normal">{activeAccount.updatedAt}</span>
                </p>
                <p className="text-subtext text-lg font-semibold">
                  Account status:{" "}
                  <span className="font-normal">{activeAccount.status}</span>
                </p>
                <p className="text-subtext text-lg font-semibold">
                  Account type:{" "}
                  <span className="font-normal">{activeAccount.type}</span>
                </p>
              </div>
            </div>
            {/* RIGHT SEGMENT */}
            <div className="w-1/3 border border-gray-500">
              <p>Third segment</p>
            </div>
          </div>
        </div>
      </>
    );
  } else {
    return (
      <div className="loader absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"></div>
    );
  }
}
