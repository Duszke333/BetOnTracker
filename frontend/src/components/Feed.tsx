import { useMemo, useState } from "react";
import type { RssChannel, RssItem } from "../types/types.ts";
import Item from "./Item.tsx";
import Tag from "./Tag.tsx";

export default function Feed({ description, items }: RssChannel) {
  const [selectedTag, setSelectedTag] = useState<string | null>(null);

  const tags = useMemo(
    () => [
      ...new Set(items.flatMap((item) => item.tags.map((tag) => tag.name))),
    ],
    [items],
  );

  return (
    <div className="p-4 m-4 flex flex-col items-center bg-zinc-200 dark:bg-zinc-700 rounded-lg shadow-lg">
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
        .filter(
          (item) =>
            !selectedTag || item.tags.some((tag) => tag.name === selectedTag),
        )
        .map((item: RssItem) => (
          <Item
            key={item.id}
            {...item}
            selectedTag={selectedTag}
            setSelectedTag={setSelectedTag}
          />
        ))}
    </div>
  );
}
