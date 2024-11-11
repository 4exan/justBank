import React from "react";
import { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import AccountService from "../service/AccountService";
import TransactionService from "../service/TransactionService";
import TransactionModal from "../modal/CreateTransactionModal";

export default function Dashboard() {
  const { logout } = useAuth();
  const [loading, setLoading] = useState(true);
  const [accounts, setAccounts] = useState([]);
  const [sortedAccounts, setSortedAccounts] = useState([]);
  const [activeAccount, setActiveAccount] = useState({});
  const [transactionsType, setTransactionsType] = useState("Incoming");
  const [incomingTransactions, setIncomingTransactions] = useState([]);
  const [outgoingTransactions, setOutgoingTransactions] = useState([]);
  const [transactionStatistic, setTransactionStatistic] = useState({});
  const [isModalOpen, setisModalOpen] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    fetchTransactions();
  }, [activeAccount, transactionsType]);

  const fetchTransactions = async () => {
    setLoading(true);
    setIncomingTransactions([]);
    setOutgoingTransactions([]);
    try {
      const token = localStorage.getItem("token");
      const transactionData = await TransactionService.getAllTransaction(
        token,
        activeAccount.accountNumber,
      );
      console.log(transactionData);
      if (transactionData?.incomingTransactionList) {
        setIncomingTransactions(() => transactionData.incomingTransactionList);
      } else {
        console.log("No incoming transactions");
      }
      if (transactionData?.outgoingTransactionList) {
        setOutgoingTransactions(() => transactionData.outgoingTransactionList);
      } else {
        console.log("No outgoing transactions");
      }
      setTransactionStatistic(() => transactionData.transactionStatistics);
    } catch (e) {
      throw e;
    } finally {
      setLoading(false);
    }
  };

  const fetchData = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const accountData = await AccountService.getMyAccounts(token);
      if (accountData?.accountList) {
        console.log(accountData.accountList);
        setAccounts(() => accountData.accountList);
        setSortedAccounts(() =>
          accountData.accountList.sort((a, b) => a.accountId - b.accountId),
        );
        setActiveAccount(() => accountData.accountList[0]);
        console.log(accounts);
      } else {
        console.log("Empty data!");
      }
    } catch (e) {
      throw e;
    } finally {
      setLoading(false);
    }
  };

  const handleAccountChange = (e) => {
    const selectedValue = e.target.value;
    const selectedAccount = accounts.find(
      (account) => account.accountNumber === selectedValue,
    );
    setActiveAccount(selectedAccount);
  };

  const handleTransactionTypeChange = (e) => {
    const selectedType = e.target.value;
    setTransactionsType(selectedType);
    console.log(`Selected type: ${transactionsType}`);
  };

  const toggleIsOpen = () => {
    setisModalOpen(() => !isModalOpen);
  };

  if (!loading) {
    return (
      <>
        <TransactionModal
          isOpen={isModalOpen}
          setIsOpen={toggleIsOpen}
          accountNumber={activeAccount.accountNumber}
          accountCurrency={activeAccount.currency}
        />
        <div className="px-4">
          <ul className="flex">
            <li>
              <select
                className="p-2 ml-2 bg-surface-1 rounded-xl text-green-1"
                onChange={(e) => handleAccountChange(e)}
                value={activeAccount.accountNumber}
              >
                {sortedAccounts.map((account) => (
                  <option
                    key={account.accountId}
                    value={account.accountNumber}
                  >{`${account.type} ${account.currency} ${account.accountNumber}`}</option>
                ))}
              </select>
            </li>
            <li className="ml-auto">
              <div className="p-2 rounded-xl text-subtext">
                <p>
                  <span className="mr-2 bg-white bg-opacity-5 p-2 rounded-xl">
                    USD/UAH - <span className="text-green-1">41.34₴</span>
                  </span>
                  <span className="mr-2 bg-white bg-opacity-5 p-2 rounded-xl">
                    JPY/UAH - <span className="text-green-1">0.27₴</span>
                  </span>
                  <span className="mr-2 bg-white bg-opacity-5 p-2 rounded-xl">
                    EUR/UAH - <span className="text-customred">44.08₴</span>
                  </span>
                  <span className="mr-2 bg-white bg-opacity-5 p-2 rounded-xl">
                    EUR/UAH - <span className="text-green-1">44.08₴</span>
                  </span>
                  <span className="mr-2 bg-white bg-opacity-5 p-2 rounded-xl">
                    CHF/UAH - <span className="text-customred">46.99₴</span>
                  </span>
                </p>
              </div>
            </li>
            <li className="ml-auto">
              <button
                className="p-2 text-text font-semibold bg-customred rounded-xl transition-all hover:bg-white hover:text-base"
                onClick={logout}
              >
                Logout
              </button>
            </li>
          </ul>
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
              </div>
              <div className="">
                <div className="mt-2 p-2 bg-white bg-opacity-5 rounded-3xl">
                  <p className="text-xl text-subtext">Monthly total:</p>
                  <div className="mt-2 p-2 bg-white bg-opacity-5 rounded-xl">
                    <div className="py-2 flex justify-between text-subtext border-b border-gray-500">
                      <p className="text-xl inline">Income:</p>
                      <p className="text-3xl inline ml-auto">
                        <span className="text-green-0 mr-2">+</span>
                        {transactionStatistic.monthTotalIncome}{" "}
                        <span className="text-lg">
                          {activeAccount.currency}
                        </span>
                      </p>
                    </div>
                    <div className="py-2 flex justify-between text-subtext">
                      <p className="text-xl inline">Spending:</p>
                      <p className="text-3xl inline ml-auto">
                        <span className="text-customred mr-2">-</span>
                        {transactionStatistic.monthTotalSpending}{" "}
                        <span className="text-lg">
                          {activeAccount.currency}
                        </span>
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              <div className="mt-2 p-2 bg-surface-0 rounded-3xl">
                <p className="text-xl text-subtext">Previous month:</p>
                <div className="mt-2 p-2 bg-white bg-opacity-5 rounded-xl">
                  <div className="py-2 flex justify-between text-subtext border-b border-gray-500">
                    <p className="text-xl inline">Income:</p>
                    <p className="text-3xl inline ml-auto">
                      <span className="text-green-0 mr-2">+</span>
                      {transactionStatistic.lastMonthTotalIncome}{" "}
                      <span className="text-lg">{activeAccount.currency}</span>
                    </p>
                  </div>
                  <div className="py-2 flex justify-between text-subtext">
                    <p className="text-xl inline">Spending:</p>
                    <p className="text-3xl inline ml-auto">
                      <span className="text-customred mr-2">-</span>
                      {transactionStatistic.lastMonthTotalSpending}{" "}
                      <span className="text-lg">{activeAccount.currency}</span>
                    </p>
                  </div>
                </div>
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
                  <span className="font-normal">
                    {activeAccount.accountNumber}
                  </span>
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
              <div className="p-2">
                <button
                  className="bg-green-1 p-2 text-base text-3xl rounded-xl transition-all hover:bg-white"
                  onClick={toggleIsOpen}
                >
                  Create new transaction
                </button>
              </div>
            </div>
            {/* RIGHT SEGMENT */}
            <div className="w-1/3">
              <div className="mt-2 p-2 bg-surface-0 rounded-3xl">
                <ul className="flex">
                  <li>
                    <p className="text-xl text-subtext">Transactions:</p>
                  </li>
                  <li className="ml-auto mr-2">
                    <select
                      className="bg-surface-0 text-lg text-subtext"
                      onChange={(e) => handleTransactionTypeChange(e)}
                      value={transactionsType}
                    >
                      <option value={"Incoming"}>Incoming</option>
                      <option value={"Outgoing"}>Outgoing</option>
                    </select>
                  </li>
                </ul>
                <div className=" my-2 p-2 rounded-xl">
                  {transactionsType === "Incoming" ? (
                    incomingTransactions.length != 0 ? (
                      incomingTransactions.map((t) => (
                        <div
                          className={`bg-white bg-opacity-5 rounded-3xl p-2 my-2 flex`}
                        >
                          <p className="text-subtext text-lg">
                            <span className="text-lg font-semibold text-green-0">
                              +{" "}
                            </span>
                            <span className="text-text">
                              {t.amount} {t.currency}
                            </span>{" "}
                            {t.description}
                          </p>
                          <button className="ml-auto px-2 bg-green-1 rounded-full transition-all hover:bg-white hover:text-base">{`>`}</button>
                        </div>
                      ))
                    ) : (
                      <p className="text-center text-lg text-subtext">
                        No transactions
                      </p>
                    )
                  ) : outgoingTransactions.length != 0 ? (
                    outgoingTransactions.map((t) => (
                      <div
                        className={`bg-white bg-opacity-5 rounded-3xl p-2 my-2 flex`}
                      >
                        <p className="text-subtext text-lg">
                          <span className="text-lg font-semibold text-customred">
                            -{" "}
                          </span>
                          <span className="text-text">
                            {t.amount} {t.currency}
                          </span>{" "}
                          {t.description}
                        </p>
                        <button className="ml-auto px-2 bg-green-1 rounded-full transition-all hover:bg-white hover:text-base">{`>`}</button>
                      </div>
                    ))
                  ) : (
                    <p className="text-center text-lg text-subtext">
                      No transactions
                    </p>
                  )}
                </div>
              </div>
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
