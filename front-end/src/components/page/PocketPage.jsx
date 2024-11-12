import React from "react";
import { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import PocketService from "../service/PocketService";
import Pocket from "../common/Pocket";

export default function PocketPage() {
  const [loading, setLoading] = useState(true);
  const [pocketList, setPocketList] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const token = localStorage.getItem("token");
      const pocketData = await PocketService.getMyPockets(token);
      if (pocketData?.pocketList) {
        setPocketList(() => pocketData.pocketList);
      }
    } catch (e) {
      throw e;
    } finally {
      setLoading(false);
    }
  };

  if (!loading) {
    return (
      <>
        <div className="py-2 px-4">
          {pocketList.map((pocket) => (
            <Pocket pocket={pocket} key={pocket.accountId} />
          ))}
        </div>
      </>
    );
  } else {
    return (
      <div className="loader absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"></div>
    );
  }
}
