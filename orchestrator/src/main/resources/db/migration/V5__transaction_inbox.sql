CREATE TABLE txno_inbox
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

CREATE INDEX idx_txno_inbox ON txno_inbox (processed, blocked, nextattempttime);
CREATE INDEX idx_txno_inbox_topic ON txno_inbox (topic, processed, seq);