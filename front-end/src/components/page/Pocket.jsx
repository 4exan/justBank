import React from "react";
import { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";

export default function Pocket() {
  const [loading, setLoading] = useState(true);
  const [companies, setCompanies] = useState([]);
  const [myShares, setMyShares] = useState([]);
  const [accounts, setAccounts] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const token = localStorage.getItem("token");
      const [companiesData, sharesData, accountsData] = await Promise.all([
        fetchCompaniesData(),
        fetchSharesData(),
        fetchAccountsData(),
      ]);
    } catch (e) {
      throw e;
    } finally {
      setLoading(false);
    }
  };

  if (!loading) {
    return (
      <>
        <p>Pocket</p>
      </>
    );
  } else {
    return (
      <div className="loader absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"></div>
    );
  }
}
