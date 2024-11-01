import { useState } from "react";
import AuthService from "../service/AuthService";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

export default function ({ isOpen, setIsOpen }) {
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmitForm = async (e) => {
    e.preventDefault();

    try {
      const response = await AuthService.login(phone, password);
      if (response.token) {
        localStorage.setItem("token", response.token);
        login();
        setIsOpen();
        navigate("/dashboard");
      } else {
        setError(response.message);
      }
    } catch (e) {
      setError(e);
      setTimeout(() => {
        setError("");
      }, 5000);
    }
  };

  return (
    <>
      {isOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-base border border-surface-1 p-6 rounded w-fit">
            <div className="flex justify-between p-2">
              <p className="text-3xl text-text font-semibold">Login</p>
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
                  placeholder="Phone number"
                  name="phone"
                  value={phone}
                  onChange={(e) => setPhone(e.target.value)}
                />
                <br />
                <input
                  className="p-2 rounded-xl bg-surface-1 focus:outline-none transition-all text-subtext"
                  type="password"
                  placeholder="Password"
                  name="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
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
