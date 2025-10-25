export default interface Article {
  id: string;
  title: string;
  articleUrl: string;
  oneLineSummary: string;
  keywords: string[];
  importanceScore: number;
  sentimentScore: number;
  sourceReliabilityScore: number;
}
