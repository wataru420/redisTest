drop table if exists ranking;
create table ranking(
  id VARCHAR(12) NOT NULL PRIMARY KEY,
  point BIGINT NOT NULL,
  INDEX USING HASH (point)
) ENGINE=MEMORY;
