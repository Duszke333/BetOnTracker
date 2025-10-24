import { createRoot } from "react-dom/client";
import Feed from "./components/Feed.tsx";
import "./styles.css";
import { BrowserRouter, Route, Routes } from "react-router";
import ManagementPanel from "./components/ManagementPanel.tsx";

createRoot(document.getElementById("root")!).render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Feed />} />

      <Route path="/management" element={<ManagementPanel />} />

      <Route path="/400" element={"Bad Request"} />
    </Routes>
  </BrowserRouter>,
);
