CREATE TABLE website
(
    id              UUID PRIMARY KEY,
    url             VARCHAR(2048) NOT NULL UNIQUE,
    title           VARCHAR(1024),
    etag            VARCHAR(512),
    created_at      TIMESTAMPTZ   NOT NULL DEFAULT now(),
    last_fetched_at TIMESTAMPTZ,
    reference_count INT           NOT NULL DEFAULT 1
);
