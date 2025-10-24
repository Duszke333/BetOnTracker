import type { Channel, Item } from "../types/types.ts";
import RssItem from "./RssItem.tsx";

export default function RssFeed({ description, items }: Channel) {
  return (
    <div className="p-4 m-4 bg-zinc-200 dark:bg-zinc-700 rounded-lg shadow-lg">
      <p className="text-lg text-center">{description}</p>
      <div className="flex flex-col items-center">
        {items.map((item: Item) => (
          <RssItem key={item.id} {...item} />
        ))}
      </div>
    </div>
  );
}
