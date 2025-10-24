export interface Item {
  id: string;
  title: string;
  link: string;
  description: string;
  pubDate: Date;
}

export interface Channel {
  title: string;
  description: string;
  items: Item[];
}
