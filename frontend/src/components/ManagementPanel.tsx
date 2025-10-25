import { useEffect, useState } from "react";
import { Link } from "react-router";
import type { Category } from "../types/Category.ts";
import TopBar from "./TopBar.tsx";

export default function ManagementPanel() {
  const [submitting, setSubmitting] = useState(false);
  const [categories, setCategories] = useState<Category[]>([]);
  const [newCategoryName, setNewCategoryName] = useState("");

  useEffect(() => {
    fetch("/api/v1/feed-management/categories/fetch")
      .then((res) => res.json() as Promise<{ categories: Category[] }>)
      .then((res) => setCategories(res.categories))
      .catch((err) => alert(err));
  }, []);

  const createNewCategory = async () => {
    setSubmitting(true);

    try {
      const res = await fetch("/api/v1/feed-management/categories/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          categoryName: newCategoryName,
        }),
      });

      if (!res.ok) {
        alert(`${res.status} ${res.statusText}`);
        return;
      }

      const createdCategory: Category = await res.json();
      setCategories((prev) => [...prev, createdCategory]);
      setNewCategoryName("");
    } catch {
      alert("Wystąpił błąd podczas dodawania kategorii.");
    } finally {
      setSubmitting(false);
    }
  };

  const deleteCategory = async (categoryId: string) => {
    try {
      const res = await fetch(
        `/api/v1/feed-management/categories/${categoryId}/delete`,
        {
          method: "DELETE",
        },
      );

      if (!res.ok) {
        alert(`${res.status} ${res.statusText}`);
        return;
      }

      setCategories((prev) =>
        prev.filter((category) => category.categoryId !== categoryId),
      );
    } catch {
      alert("Wystąpił błąd podczas usuwania kategorii.");
    }
  };

  return (
    <>
      <TopBar
        headerStart={
          <p className="text-2xl font-bold text-[#AA4344]">Management Panel</p>
        }
        headerEnd={
          <Link className="font-bold" to="/">
            Feed RSS
          </Link>
        }
      />
      <div className="p-4 my-4 mx-8 flex flex-col bg-zinc-200 dark:bg-zinc-700 rounded-lg shadow-lg">
        <div className="p-4 flex items-center">
          <h1 className="text-3xl font-bold">Categories</h1>
          <div className="ml-auto flex gap-2">
            <input
              className="px-2 py-1 rounded-md bg-zinc-300 dark:bg-zinc-800"
              type="text"
              value={newCategoryName}
              onChange={(e) => setNewCategoryName(e.target.value)}
              placeholder="New category name..."
            />
            <button
              className={`
                px-4 py-2 rounded-full text-white font-bold transition
                ${submitting ? "bg-gray-400 cursor-not-allowed" : "cursor-pointer bg-[#AA4344] hover:bg-[#c25555] active:bg-[#882b2c]"}
              `}
              type="button"
              onClick={createNewCategory}
              disabled={submitting}
            >
              {submitting ? "Adding..." : "Add"}
            </button>
          </div>
        </div>
        <div className="grid grid-cols-5 gap-6 mt-4">
          {categories.map((category: Category) => (
            <button
              type="button"
              key={category.categoryId}
              className="category-item p-4 text-center bg-zinc-100 dark:bg-zinc-900 rounded-3xl shadow-md cursor-pointer"
              onClick={() => deleteCategory(category.categoryId)}
            >
              <span className="category-name">{category.categoryName}</span>
              <div className="trash-icon hidden">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <path d="M3 6h18" />
                  <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6" />
                  <path d="M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" />
                </svg>
              </div>
            </button>
          ))}
        </div>
      </div>
    </>
  );
}
