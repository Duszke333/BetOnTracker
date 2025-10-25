import type Article from "../types/Article.ts";
import Pill from "./Pill.tsx";

export default function Item({
  title,
  articleUrl,
  oneLineSummary,
  keywords,
  importanceScore,
  sentimentScore,
  selectedTag,
  setSelectedTag,
}: Article & {
  selectedTag: string | null;
  setSelectedTag: (category: string | null) => void;
}) {
  return (
    <div className="mt-4 p-4 bg-zinc-100 dark:bg-zinc-900 rounded-lg max-w-4xl w-full shadow-md">
      <div className="flex flex-col gap-4">
        <a href={articleUrl} target="_blank" rel="noopener noreferrer">
          <h2 className="text-xl font-bold hover:underline">{title}</h2>
        </a>
        <div className="flex flex-row gap-2 items-center overflow-x-auto">
          {keywords.map((keyword) => (
            <Pill
              key={keyword}
              name={keyword}
              selectedItem={selectedTag}
              setSelectedItem={setSelectedTag}
            />
          ))}
        </div>
        <p className="font-bold text-[#AA4344]">
          Ważność: {importanceScore} Sentyment: {sentimentScore}
        </p>
      </div>
      <p className="mt-2">{oneLineSummary}</p>
    </div>
  );
}
