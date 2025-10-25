import { useState } from "react";
import type Article from "../types/Article.ts";
import Pill from "./Pill.tsx";

export default function Item({
  id,
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
  const [summary, setSummary] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const fetchSummary = async () => {
    if (summary) {
      setSummary(null);
      return;
    }
    setIsLoading(true);
    try {
      const res = await fetch(`/api/v1/articles/${id}/summary`);
      const data = await res.json();
      setSummary(data.summary);
    } catch (err) {
      console.error(err);
    } finally {
      setIsLoading(false);
    }
  };

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
      <div className="flex justify-end">
        <button
          type="button"
          onClick={fetchSummary}
          className="focus:outline-none"
        >
          <svg
            className={`w-6 h-6 text-gray-500 dark:text-gray-400 transform transition-transform ${
              summary ? "rotate-180" : ""
            }`}
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M19 9l-7 7-7-7"
            />
          </svg>
        </button>
      </div>
      {isLoading && (
        <p className="mt-2 text-center">Ładowanie podsumowania...</p>
      )}
      {summary && (
        <div className="mt-4 p-4 bg-zinc-200 dark:bg-zinc-800 rounded-lg">
          <h3 className="text-lg font-bold">Podsumowanie</h3>
          <p className="mt-2">{summary}</p>
        </div>
      )}
    </div>
  );
}
