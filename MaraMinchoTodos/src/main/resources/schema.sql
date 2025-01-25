
CREATE TABLE users
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) not NULL
);

CREATE TABLE todos
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    is_show BOOLEAN,
    user_id INT NOT NULL,
    title   VARCHAR(256) NOT NULL,
    content VARCHAR(256) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE archived_todos
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    original_id INT,
    user_id  INT          NOT NULL,
    title   VARCHAR(256) NOT NULL,
    content VARCHAR(256) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (original_id) REFERENCES todos (id) ON DELETE CASCADE
)