import { useEffect, useMemo, useState } from "react";
import { Link, Navigate } from "react-router";
import text from "@/assets/wiadomosci_ztm_rss.xml?raw";
import parseRss from "../scripts/rss.ts";
import type { Category } from "../types/Category.ts";
import Item, { type RssItem } from "./Item.tsx";
import SearchableRow from "./SearchableRow.tsx";
import TopBar from "./TopBar.tsx";

export default function Feed() {
  const { title, description, link, items } = parseRss(text);
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

  const tags = useMemo(
    () => [...new Set(items.flatMap((item) => item.tags))],
    [items],
  );

  if (!title || !description || !link) {
    return <Navigate to="/400" replace />;
  }

  const filteredItems = items.filter((item) => {
    const tagMatch = !selectedTag || item.tags.includes(selectedTag);
    const categoryMatch =
      !selectedCategory || item.tags.includes(selectedCategory);
    return tagMatch && categoryMatch;
  });

  return (
    <>
      <TopBar
        headerStart={
          <p className="text-2xl font-bold text-[#AA4344]">{title}</p>
        }
        headerEnd={
          <Link className="font-bold" to="/management">
            Panel obsługi
          </Link>
        }
      />
      <div className="p-4 my-4 mx-8 flex flex-col items-center bg-zinc-200 dark:bg-zinc-700 rounded-lg shadow-lg">
        <div className="flex flex-col">
          <p className="text-lg">{description}</p>
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
        {filteredItems.map((item: RssItem) => (
          <Item
            key={item.id}
            {...item}
            selectedTag={selectedTag || selectedCategory}
            setSelectedTag={(tag) => {
              setSelectedTag(tag);
              setSelectedCategory(tag);
            }}
          />
        ))}
      </div>
    </>
  );
}
