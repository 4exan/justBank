/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        base: "#25272d",
        surface: {
          0: "#2e3137",
          1: "#34373e",
          2: "#2e3035",
        },
        text: "#f4f5f4",
        subtext: "#b3b4b3",
        green: {
          0: "#00f597",
          1: "#b1ee81",
        },
        customred: "#fb5d5d",
      },
    },
  },
  plugins: [],
};
