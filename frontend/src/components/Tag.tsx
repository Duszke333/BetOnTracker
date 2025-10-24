export default function Tag({
  name,
  selectedTag,
  setSelectedTag,
}: {
  name: string;
  selectedTag: string | null;
  setSelectedTag: (category: string | null) => void;
}) {
  return (
    <button
      type="button"
      onClick={() => setSelectedTag(selectedTag === name ? null : name)}
      className={`py-1 px-2 m-2 rounded-full text-sm shadow-sm cursor-pointer ${
        name === selectedTag
          ? "text-zinc-100 bg-zinc-600"
          : "bg-zinc-400 dark:bg-zinc-800"
      }`}
    >
      {name}
    </button>
  );
}
