import text from "@/assets/wiadomosci_ztm_rss.xml?raw";
import RssFeed from "./components/RssFeed.tsx";

export default function App() {
  const parser = new DOMParser();
  const xml = parser.parseFromString(text, "application/xml");

  const title = xml.querySelector("channel > title")?.textContent;
  const description = xml.querySelector("channel > description")?.textContent;
  const link = xml.querySelector("channel > link")?.textContent;
  const items = Array.from(xml.querySelectorAll("channel > item")).map(
    (item) => ({
      id: crypto.randomUUID(),
      title: item.querySelector("title")?.textContent ?? "",
      link: item.querySelector("link")?.textContent ?? "",
      description: item.querySelector("description")?.textContent ?? "",
      pubDate: new Date(item.querySelector("pubDate")?.textContent ?? ""),
    }),
  );

  if (!title || !description || !link) {
    return <div>Error page here</div>;
  }

  return <RssFeed title={title} description={description} items={items} />;
}
