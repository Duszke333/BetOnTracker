import type { Channel, Item } from "../types/types.ts";
import RssItem from "./RssItem.tsx";

export default function RssFeed({ title, description, items }: Channel) {
  return (
    <div className="p-4 bg-zinc-200">
      <h1 className="text-2xl font-bold text-[#AA4344] text-center">{title}</h1>
      <p className="text-lg text-center">{description}</p>
      <p className="text-lg mt-4 text-center">Artykuły:</p>
      <div className="flex flex-col items-center">
        {items.map((item: Item) => (
          <RssItem key={item.id} {...item} />
        ))}
      </div>
    </div>
  );
}
