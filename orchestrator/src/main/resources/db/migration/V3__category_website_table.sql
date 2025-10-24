CREATE TABLE category_website
(
    category_id SERIAL NOT NULL REFERENCES category (id) ON DELETE CASCADE,
    website_id  UUID   NOT NULL REFERENCES website (id) ON DELETE CASCADE,
    PRIMARY KEY (category_id, website_id)
);