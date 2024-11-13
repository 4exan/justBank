import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthService from "../service/AuthService";
import { useAuth } from "../context/AuthContext";
import LoginModal from "../modal/LoginModal";

export default function Navbar() {
  const [activePage, setActivePage] = useState("Dashboard");
  const [isLoginOpen, setIsLoginOpen] = useState(false);
  const [isRegistrationOpen, setIsRegistrationOpen] = useState(false);
  const { isAuthenticated, isAdmin } = useAuth();

  const toggleLoginModal = () => {
    setIsLoginOpen(() => !isLoginOpen);
  };

  return (
    <>
      <LoginModal isOpen={isLoginOpen} setIsOpen={toggleLoginModal} />
      <div className="bg-surface-0 m-4 p-4 rounded-full">
        <ul className="flex justify-between items-center p-2">
          {
            <li className="mr-2 inline">
              <Link
                to="/"
                className={`${activePage === "Home" ? "bg-white text-base" : "bg-green-1 text-base"} py-2 px-4 rounded-full transition-all`}
                onClick={(e) => setActivePage(e.target.innerText)}
              >{`Home`}</Link>
            </li>
          }
          {isAuthenticated && (
            <li className="mr-2 inline">
              <Link
                to="/dashboard"
                className={`${activePage === "Dashboard" ? "bg-opacity-100 text-base" : "bg-opacity-5 text-subtext"} py-2 px-4 bg-white rounded-full transition-all`}
                onClick={(e) => setActivePage(e.target.innerText)}
              >
                Dashboard
              </Link>
            </li>
          )}
          {isAuthenticated && (
            <li className="mr-2 inline">
              <Link
                to="/pocket"
                className={`${activePage === "Pocket" ? "bg-opacity-100 text-base" : "bg-opacity-5 text-subtext"} py-2 px-4 bg-white rounded-full transition-all`}
                onClick={(e) => setActivePage(e.target.innerText)}
              >
                Pocket
              </Link>
            </li>
          )}
          {isAuthenticated && (
            <li className="mr-2 inline">
              <Link
                to="/investment"
                className={`${activePage === "Investment" ? "bg-opacity-100 text-base" : "bg-opacity-5 text-subtext"} py-2 px-4 bg-white rounded-full transition-all`}
                onClick={(e) => setActivePage(e.target.innerText)}
              >
                Investment
              </Link>
            </li>
          )}
          {isAuthenticated && (
            <li className="mr-2 inline">
              <Link
                to="/search"
                className={`${activePage === "Search" ? "bg-opacity-100 text-base" : "bg-opacity-5 text-subtext"} py-2 px-4 bg-white rounded-full transition-all`}
                onClick={(e) => setActivePage(e.target.innerText)}
              >
                Search
              </Link>
            </li>
          )}
          {isAuthenticated && (
            <li className="mr-2 inline">
              <Link
                to="/notification"
                className={`${activePage === "Notification" ? "bg-opacity-100 text-base" : "bg-opacity-5 text-subtext"} py-2 px-4 bg-white rounded-full transition-all`}
                onClick={(e) => setActivePage(e.target.innerText)}
              >
                Notification
              </Link>
            </li>
          )}
          {isAuthenticated && (
            <li className="mr-2 inline">
              <Link
                to="/settings"
                className={`${activePage === "Settings" ? "bg-opacity-100 text-base" : "bg-opacity-5 text-subtext"} py-2 px-4 bg-white rounded-full transition-all`}
                onClick={(e) => setActivePage(e.target.innerText)}
              >
                Settings
              </Link>
            </li>
          )}
          {isAuthenticated && (
            <li className="inline ml-auto">
              <Link
                to="/premium"
                className="py-2 px-4 bg-green-1 text-base shadow-lg rounded-full transition-all hover:bg-white hover:text-base"
              >
                Premium Upgrade
              </Link>
            </li>
          )}
          {!isAuthenticated && (
            <li className="inline ml-auto">
              <Link
                onClick={toggleLoginModal}
                className="py-2 px-4 bg-green-1 text-base shadow-lg rounded-full transition-all hover:bg-white hover:text-base"
              >
                Login
              </Link>
              <Link
                onClick={() => console.log("Registration")}
                className="py-2 px-4 ml-4 bg-surface-1 text-subtext shadow-lg rounded-full transition-all hover:bg-white hover:text-base"
              >
                Registration
              </Link>
            </li>
          )}
        </ul>
      </div>
    </>
  );
}
