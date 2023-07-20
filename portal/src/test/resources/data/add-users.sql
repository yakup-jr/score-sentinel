INSERT INTO users_schema.users (id, username, email, password, created_at, last_online, is_active)
VALUES (1, 'user1', 'user1@example.com', 'password1', NOW(), NOW(), TRUE),
       (2, 'user2', 'user2@example.com', 'password2', NOW(), NOW(), FALSE),
       (3, 'user3', 'user3@example.com', 'password3', NOW(), NOW(), TRUE),
       (4, 'user4', 'user4@example.com', 'password4', NOW(), NOW(), FALSE);
