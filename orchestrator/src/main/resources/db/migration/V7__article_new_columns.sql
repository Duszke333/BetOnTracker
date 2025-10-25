ALTER TABLE article
    ADD COLUMN title                    VARCHAR(255)              NOT NULL,
    ADD COLUMN short_summary            VARCHAR(2048),
    ADD COLUMN keywords                 jsonb DEFAULT '[]'::jsonb,
    ADD COLUMN importance_score         SMALLINT,
    ADD COLUMN sentiment_score          SMALLINT,
    ADD COLUMN source_reliability_score SMALLINT;

-- Optional: enforce 1-to-5 range
ALTER TABLE article
    ADD CONSTRAINT chk_importance_score CHECK (importance_score BETWEEN 1 AND 5),
    ADD CONSTRAINT chk_sentiment_score CHECK (sentiment_score BETWEEN 1 AND 5),
    ADD CONSTRAINT chk_source_reliability_score CHECK (source_reliability_score BETWEEN 1 AND 5);
