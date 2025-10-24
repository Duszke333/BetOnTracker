import text from "@/assets/wiadomosci_ztm_rss.xml?raw";

export default function RssFeed() {
  const parser = new DOMParser();
  const xml = parser.parseFromString(text, "application/xml");

  const title = xml.querySelector("channel > title");
  const description = xml.querySelector("channel > description");
  const items = Array.from(xml.querySelectorAll("channel > item")).map(
    (item) => ({
      id: crypto.randomUUID(),
      title: item.querySelector("title")?.textContent,
      link: item.querySelector("link")?.textContent,
      description: item.querySelector("description")?.textContent,
      pubDate: item.querySelector("pubDate")?.textContent,
    }),
  );

  return (
    <>
      <h1>{title?.textContent}</h1>
      <p>{description?.textContent}</p>
      <p>Artykuły:</p>
      {items.map((item) => {
        return (
          <div key={item.id}>
            <h2>{item.title}</h2>
            <div
              style={{
                display: "flex",
                alignItems: "center",
                flexDirection: "row",
              }}
            >
              <a href={item.link}>🔗</a>
              <p>{item.pubDate}</p>
            </div>
            <p>{item.description}</p>
          </div>
        );
      })}
    </>
  );
}
