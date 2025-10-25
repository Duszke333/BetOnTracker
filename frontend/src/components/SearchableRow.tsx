import Pill from "./Pill.tsx";

export default function SearchableRow({
  items,
  selectedItem,
  setSelectedItem,
  searchTerm,
  setSearchTerm,
  title,
}: {
  items: string[];
  selectedItem: string | null;
  setSelectedItem: (item: string | null) => void;
  searchTerm: string;
  setSearchTerm: (term: string) => void;
  title: string;
}) {
  return (
    <div className="my-2 flex flex-row items-center max-w-4xl">
      <h3 className="text-lg font-bold mr-2">{title}</h3>
      <input
        type="text"
        placeholder="Search..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="px-2 py-1 rounded-md bg-zinc-300 dark:bg-zinc-800 mr-2"
      />
      <div className="flex flex-row gap-2 items-center overflow-x-auto">
        {items
          .filter((item) =>
            item.toLowerCase().includes(searchTerm.toLowerCase()),
          )
          .map((item) => (
            <Pill
              key={item}
              name={item}
              selectedItem={selectedItem}
              setSelectedItem={setSelectedItem}
            />
          ))}
      </div>
    </div>
  );
}
