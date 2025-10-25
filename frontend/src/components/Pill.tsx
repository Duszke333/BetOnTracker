export default function Pill({
  name,
  selectedItem,
  setSelectedItem,
}: {
  name: string;
  selectedItem: string | null;
  setSelectedItem: (name: string | null) => void;
}) {
  return (
    <button
      type="button"
      onClick={() => setSelectedItem(selectedItem === name ? null : name)}
      className={`py-1 px-2 rounded-full text-sm shadow-sm cursor-pointer ${
        name === selectedItem
          ? "text-zinc-100 bg-zinc-600"
          : "bg-zinc-400 dark:bg-zinc-800"
      }`}
    >
      {name}
    </button>
  );
}
