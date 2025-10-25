import { Moon, Sun } from "lucide-react";
import { useEffect, useState } from "react";

export default function TopBar({ title }: { title: string }) {
  const [theme, setTheme] = useState(
    localStorage.theme === "dark" ||
      (!("theme" in localStorage) &&
        window.matchMedia("(prefers-color-scheme: dark)").matches)
      ? "dark"
      : "light",
  );

  useEffect(() => {
    if (theme === "dark") {
      document.documentElement.classList.add("dark");
    } else {
      document.documentElement.classList.remove("dark");
    }
    localStorage.theme = theme;
  }, [theme]);

  return (
    <div className="p-4 flex justify-between items-center">
      <h1 className="text-2xl font-bold text-[#AA4344]">{title}</h1>
      <button
        type={"button"}
        onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
        className="p-2 cursor-pointer"
      >
        {theme === "dark" ? <Sun /> : <Moon />}
      </button>
    </div>
  );
}
