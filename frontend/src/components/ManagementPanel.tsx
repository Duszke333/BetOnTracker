import { useEffect, useState } from "react";
import { Link } from "react-router";
import type { Category } from "../types/Category.ts";
import type { Website } from "../types/Website.ts";
import Pill from "./Pill.tsx";
import TopBar from "./TopBar.tsx";

export default function ManagementPanel() {
  const [submitting, setSubmitting] = useState(false);
  const [categories, setCategories] = useState<Category[]>([]);
  const [newCategoryName, setNewCategoryName] = useState("");
  const [websites, setWebsites] = useState<Website[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);

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

  const getWebsites = async (categoryId: string) => {
    if (selectedCategory === categoryId) {
      setSelectedCategory(null);
      setWebsites([]);
      return;
    }

    try {
      const res = await fetch(
        `/api/v1/feed-management/categories/${categoryId}/get`,
      );

      if (!res.ok) {
        alert(`${res.status} ${res.statusText}`);
        return;
      }

      const returnedWebsites: Website[] = await res.json();
      setWebsites(returnedWebsites);
      setSelectedCategory(categoryId);
    } catch {
      alert("Wystąpił błąd podczas pobierania stron.");
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
            <div key={category.categoryId}>
              <button
                type="button"
                className="w-full p-4 text-center bg-zinc-100 dark:bg-zinc-900 rounded-3xl shadow-md cursor-pointer"
                onClick={() => getWebsites(category.categoryId)}
              >
                <span className="category-name">{category.categoryName}</span>
              </button>
              {selectedCategory === category.categoryId && (
                <div className="flex flex-wrap mt-2 gap-2">
                  {websites.map((website) => (
                    <Pill
                      key={website.id}
                      name={website.url}
                      selectedItem={null}
                      setSelectedItem={() => {
                        return;
                      }}
                    />
                  ))}
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </>
  );
}
