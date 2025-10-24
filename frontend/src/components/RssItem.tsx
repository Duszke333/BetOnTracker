import type { Item } from "../types/types.ts";

export default function RssItem({ link, title, description, pubDate }: Item) {
  return (
    <div className="mt-4 p-4 bg-zinc-100 dark:bg-zinc-900 rounded-lg max-w-3xl w-full shadow-md">
      <a href={link} target="_blank" rel="noopener noreferrer">
        <h2 className="text-xl font-bold hover:underline">{title}</h2>
      </a>
      <div className="flex items-center flex-row mt-2">
        <p className="font-bold text-[#AA4344]">
          {pubDate.toLocaleDateString()}
        </p>
      </div>
      <p className="mt-2">{description}</p>
    </div>
  );
}
