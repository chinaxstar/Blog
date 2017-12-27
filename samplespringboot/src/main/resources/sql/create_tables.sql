CREATE TABLE IF NOT EXISTS user (
  id       INTEGER AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(20),
  password VARCHAR(128),
  salt     VARCHAR(5),
  headUrl  VARCHAR(128),
  role     VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS login_ticket (
  id      INTEGER AUTO_INCREMENT PRIMARY KEY,
  userId  INTEGER,
  status  INTEGER,
  expired DATE,
  ticket  VARCHAR(16)
);