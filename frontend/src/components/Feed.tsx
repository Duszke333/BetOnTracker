import { useMemo, useState } from "react";
import { Link, Navigate } from "react-router";
import text from "@/assets/wiadomosci_ztm_rss.xml?raw";
import parseRss from "../scripts/rss.ts";
import Item, { type RssItem } from "./Item.tsx";
import Tag from "./Tag.tsx";
import TopBar from "./TopBar.tsx";

export default function Feed() {
  const { title, description, link, items } = parseRss(text);
  const [selectedTag, setSelectedTag] = useState<string | null>(null);
  const tags = useMemo(
    () => [...new Set(items.flatMap((item) => item.tags))],
    [items],
  );

  if (!title || !description || !link) {
    return <Navigate to="/400" replace />;
  }

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
        <p className="text-lg">{description}</p>
        <div className="flex flex-row items-center">
          {tags.map((tag) => (
            <Tag
              key={tag}
              name={tag}
              selectedTag={selectedTag}
              setSelectedTag={setSelectedTag}
            />
          ))}
        </div>
        {items
          .filter((item) => !selectedTag || item.tags.includes(selectedTag))
          .map((item: RssItem) => (
            <Item
              key={item.id}
              {...item}
              selectedTag={selectedTag}
              setSelectedTag={setSelectedTag}
            />
          ))}
      </div>
    </>
  );
}
