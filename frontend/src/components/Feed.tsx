import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router";
import type Article from "../types/Article.ts";
import type { Category } from "../types/Category.ts";
import Item from "./Item.tsx";
import SearchableRow from "./SearchableRow.tsx";
import TopBar from "./TopBar.tsx";

export default function Feed() {
  const [articles, setArticles] = useState<Article[]>([]);
  const [selectedTag, setSelectedTag] = useState<string | null>(null);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
  const [tagSearchTerm, setTagSearchTerm] = useState("");
  const [categorySearchTerm, setCategorySearchTerm] = useState("");
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    fetch("/api/v1/feed-management/categories/fetch")
      .then((res) => res.json() as Promise<{ categories: Category[] }>)
      .then((res) => setCategories(res.categories))
      .catch((err) => alert(err));
  }, []);

  useEffect(() => {
    const category = categories.find(
      (category) => category.categoryName === selectedCategory,
    );
    if (!category) return;

    fetch(
      `/api/v1/articles/fetch?${new URLSearchParams({
        categoryId: category.categoryId,
      })}`,
    )
      .then((res) => res.json() as Promise<{ articles: Article[] }>)
      .then((res) => setArticles(res.articles))
      .catch((err) => alert(err));
  }, [selectedCategory, categories]);

  const tags = useMemo(
    () => [...new Set(articles.flatMap((item) => item.keywords))],
    [articles],
  );

  const filteredArticles = articles.filter(
    (item) => !selectedTag || item.keywords.includes(selectedTag),
  );

  return (
    <>
      <TopBar
        headerStart={
          <p className="text-2xl font-bold text-[#AA4344]">BetOnTracker</p>
        }
        headerEnd={
          <Link className="font-bold" to="/management">
            Panel obsługi
          </Link>
        }
      />
      <div className="p-4 my-4 mx-8 bg-zinc-200 dark:bg-zinc-700 rounded-lg shadow-lg">
        <div className="flex flex-col">
          {/*<p className="text-lg">{description}</p>*/}
          <SearchableRow
            title="Categories"
            items={categories.map((c) => c.categoryName)}
            selectedItem={selectedCategory}
            setSelectedItem={setSelectedCategory}
            searchTerm={categorySearchTerm}
            setSearchTerm={setCategorySearchTerm}
          />
          <SearchableRow
            title="Tags"
            items={tags}
            selectedItem={selectedTag}
            setSelectedItem={setSelectedTag}
            searchTerm={tagSearchTerm}
            setSearchTerm={setTagSearchTerm}
          />
        </div>
        {filteredArticles.map((article) => (
          <Item
            key={article.id}
            {...article}
            selectedTag={selectedTag}
            setSelectedTag={setSelectedTag}
          />
        ))}
      </div>
    </>
  );
}
