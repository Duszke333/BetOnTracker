export default function parseRss(content: string) {
  const parser = new DOMParser();
  const xml = parser.parseFromString(content, "application/xml");

  const title = xml.querySelector("channel > title")?.textContent;
  const description = xml.querySelector("channel > description")?.textContent;
  const link = xml.querySelector("channel > link")?.textContent;
  const items = Array.from(xml.querySelectorAll("channel > item")).map(
    (item) => ({
      id: crypto.randomUUID(),
      title: item.querySelector("title")?.textContent ?? "",
      link: item.querySelector("link")?.textContent ?? "",
      description: item.querySelector("description")?.textContent ?? "",
      tags: [
        ...new Set(
          Array.from(item.querySelectorAll("tag")).map(
            (tag) => tag.textContent,
          ),
        ),
      ],
      pubDate: new Date(item.querySelector("pubDate")?.textContent ?? ""),
    }),
  );

  return { title, description, link, items };
}
