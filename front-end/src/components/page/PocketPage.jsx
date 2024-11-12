import React from "react";
import { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import PocketService from "../service/PocketService";
import Pocket from "../common/Pocket";
import CreatePocketModal from "../modal/CreatePocketModal";

export default function PocketPage() {
  const [loading, setLoading] = useState(true);
  const [pocketList, setPocketList] = useState([]);
  const [isPocketCreateOpen, setIsPocketCreateOpen] = useState(false);

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

  const togglePocketCreate = () => {
    setIsPocketCreateOpen(() => !isPocketCreateOpen);
  };

  if (!loading) {
    return (
      <>
        <CreatePocketModal
          isOpen={isPocketCreateOpen}
          setIsOpen={togglePocketCreate}
        />
        <div className="py-2 px-4">
          <ul className="flex justify-start gap-2">
            <li>
              {pocketList.map((pocket) => (
                <Pocket pocket={pocket} key={pocket.accountId} />
              ))}
            </li>
            <li>
              <button
                className="text-2xl p-2 w-96 h-40 max-w-96 max-h-1/3 border border-green-1 rounded-lg text-green-1 text-center transition-all hover:bg-green-1 hover:border-transparent hover:text-base-0 hover:text-2xl"
                onClick={togglePocketCreate}
              >
                Create new pocket
              </button>
            </li>
          </ul>
        </div>
      </>
    );
  } else {
    return (
      <div className="loader absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2"></div>
    );
  }
}
