INSERT INTO public.users (id, create_date, email, enabled, first_name, last_name, password, token_expired)
VALUES
(1, '2025-04-23T10:00:00', 'user1@example.com', TRUE, 'John', 'Doe', 'password123', FALSE),
(2, '2025-04-22T11:00:00', 'user2@example.com', TRUE, 'Jane', 'Smith', 'securePassword45', TRUE),
(3, '2025-04-21T09:30:00', 'user3@example.com', FALSE, 'Robert', 'Brown', 'adminPass321', FALSE),
(4, '2025-04-20T08:15:00', 'user4@example.com', TRUE, 'Alice', 'Johnson', 'alice2025!', TRUE),
(5, '2025-04-19T12:45:00', 'user5@example.com', TRUE, 'Charlie', 'Davis', 'charlie2025!', FALSE);
