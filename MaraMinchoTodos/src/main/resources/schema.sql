
CREATE TABLE users
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) not NULL
);

CREATE TABLE todos
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    isShow BOOLEAN,
    userId INT NOT NULL,
    title   VARCHAR(256) NOT NULL,
    content VARCHAR(256) NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id)
);

CREATE TABLE archived_todos
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    originalId Int,
    userId  INT          NOT NULL,
    title   VARCHAR(256) NOT NULL,
    content VARCHAR(256) NOT NULL,
    FOREIGN KEY (userId) REFERENCES users (id),
    FOREIGN KEY (originalId) REFERENCES todos (id);
)