export interface RssTag {
  id: string;
  name: string;
}

export interface RssItem {
  id: string;
  title: string;
  link: string;
  description: string;
  tags: RssTag[];
  pubDate: Date;
}

export interface RssChannel {
  title: string;
  description: string;
  items: RssItem[];
}
