import { Moon, Sun } from "lucide-react";
import { type JSX, useEffect, useState } from "react";

export default function TopBar({
  headerStart,
  headerEnd,
}: {
  headerStart: JSX.Element;
  headerEnd?: JSX.Element;
}) {
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
      {headerStart}
      <div className="flex items-center gap-2">
        {headerEnd}
        <button
          type={"button"}
          onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
          className="p-2 cursor-pointer"
        >
          {theme === "dark" ? <Sun /> : <Moon />}
        </button>
      </div>
    </div>
  );
}
