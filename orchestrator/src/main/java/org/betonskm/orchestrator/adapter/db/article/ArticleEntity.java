package org.betonskm.orchestrator.adapter.db.article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.betonskm.orchestrator.adapter.db.category.CategoryEntity;
import org.hibernate.annotations.GenericGenerator;
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
}

