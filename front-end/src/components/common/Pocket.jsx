import React from "react";

export default function Pocket({ pocket }) {
  const handleCopy = () => {
    navigator.clipboard.writeText(pocket.accountNumber);
  };

  return (
    <>
      <div className="p-2 w-96 h-40 max-w-96 max-h-1/3 bg-surface-0 rounded-lg text-subtext">
        <ul className="flex justify-between">
          <li>
            <p className="text-subtext text-opacity-35">
              {pocket.accountNumber}
            </p>
          </li>
          <li className="ml-auto">
            <button
              className="text-subtext text-opacity-35 transition-all hover:text-opacity-100 hover:text-white"
              placeholder="Copy"
              onClick={handleCopy}
            >
              Copy
            </button>
          </li>
        </ul>
        <div className="p-2 bg-white bg-opacity-5 rounded-lg mt-2">
          <p className="text-xl text-text">{pocket.name}</p>
          <p>Balance:</p>
          <p>
            {pocket.balance}/{pocket.targetAmount} {pocket.currency}
          </p>
        </div>
        <div className="mt-2 w-full bg-white bg-opacity-15 rounded-full h-2">
          <div
            className="bg-green-1 h-2 rounded-full"
            style={{
              width: `${(pocket.balance * 100) / pocket.targetAmount}%`,
            }}
          ></div>
        </div>
      </div>
    </>
  );
}

//          <progress value={25000} max={pocket.targetAmount}></progress>
