CREATE TABLE txno_version
(
    id      INTEGER NOT NULL DEFAULT 0,
    version INTEGER
);

CREATE TABLE txno_sequence
(
    topic VARCHAR(250) NOT NULL,
    seq   BIGINT       NOT NULL,
    PRIMARY KEY (topic, seq)
);

CREATE TABLE txno_outbox
(
    id              VARCHAR(36) PRIMARY KEY NOT NULL,
    invocation      text,
    nextattempttime timestamp(6),
    attempts        INT,
    blocked         BOOLEAN,
    version         INT,
    uniquerequestid VARCHAR(250),
    processed       BOOLEAN,
    lastAttemptTime timestamp(6),
    topic           VARCHAR(250) default '*'::VARCHAR NOT NULL,
    seq             BIGINT
);

CREATE INDEX idx_txno_outbox ON txno_outbox (processed, blocked, nextattempttime);
CREATE INDEX idx_txno_outbox_topic ON txno_outbox (topic, processed, seq);