package org.betonskm.orchestrator.adapter.db.article;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.betonskm.orchestrator.configuration.database.StringListJsonConverter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "article")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ArticleEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(nullable = false, columnDefinition = "UUID")
  private UUID id;

  @NotNull
  @Column(name = "category_id", nullable = false)
  private Integer categoryId;

  @Size(max = 2048)
  @NotBlank
  @Column(nullable = false, length = 2048)
  private String articleLink;

  @Size(max = 1024)
  @NotBlank
  @Column(name = "s3_article_content_path", nullable = false, length = 1024)
  private String s3ArticleContentPath;

  @Size(max = 1024)
  @Column(name = "s3_summary_path", length = 1024)
  private String s3SummaryPath;

  @CreatedDate
  @Column(nullable = false, columnDefinition = "TIMESTAMPTZ")
  private OffsetDateTime createdAt;

  @Size(max = 512)
  @Column(name = "title", length = 512)
  private String title;

  @Column(name = "short_summary", length = 2048)
  private String oneLineSummary;

  @Type(value = JsonBinaryType.class)
  @Column(name = "keywords", columnDefinition = "jsonb")
  private List<String> keywords = new ArrayList<>();

  @Column(name = "importance_score")
  private Short importanceScore; // 1–5

  @Column(name = "sentiment_score")
  private Short sentimentScore;  // 1–5

  @Column(name = "source_reliability_score")
  private Short sourceReliabilityScore; // 1–5
}

