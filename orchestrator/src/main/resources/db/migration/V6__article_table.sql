CREATE TABLE article
(
    id                      UUID PRIMARY KEY,
    category_id             INTEGER       NOT NULL,
    CONSTRAINT fk_article_category FOREIGN KEY (category_id)
        REFERENCES category (id)
        ON DELETE CASCADE,
    article_link            VARCHAR(2048) NOT NULL,
    s3_article_content_path VARCHAR(1024) NOT NULL,
    s3_summary_path         VARCHAR(1024),
    created_at              TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_article_category_id ON article (category_id);
