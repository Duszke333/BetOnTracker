CREATE TABLE category
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(255)             NOT NULL UNIQUE,
    created_at        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    decommissioned_at TIMESTAMP WITH TIME ZONE NULL
);