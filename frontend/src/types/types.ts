export interface RssItem {
  id: string;
  title: string;
  link: string;
  description: string;
  tags: string[];
  pubDate: Date;
}

export interface RssChannel {
  title: string;
  description: string;
  items: RssItem[];
}
