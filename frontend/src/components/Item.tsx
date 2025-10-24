import Tag from "./Tag.tsx";

export interface RssItem {
  id: string;
  title: string;
  link: string;
  description: string;
  tags: string[];
  pubDate: Date;
}

export default function Item({
  link,
  title,
  description,
  tags,
  pubDate,
  selectedTag,
  setSelectedTag,
}: RssItem & {
  selectedTag: string | null;
  setSelectedTag: (category: string | null) => void;
}) {
  return (
    <div className="mt-4 p-4 bg-zinc-100 dark:bg-zinc-900 rounded-lg max-w-4xl w-full shadow-md">
      <div className="flex flex-row items-center">
        <a href={link} target="_blank" rel="noopener noreferrer">
          <h2 className="text-xl font-bold hover:underline">{title}</h2>
        </a>
        {tags.map((tag) => (
          <Tag
            key={tag}
            name={tag}
            selectedTag={selectedTag}
            setSelectedTag={setSelectedTag}
          />
        ))}
      </div>
      <p className="font-bold text-[#AA4344]">{pubDate.toLocaleDateString()}</p>
      <p className="mt-2">{description}</p>
    </div>
  );
}
