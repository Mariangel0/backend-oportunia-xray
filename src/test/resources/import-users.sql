INSERT INTO public.role (id, name) VALUES (1, 'USER');
INSERT INTO public.privilege (id, name) VALUES (1, 'WRITE_PRIVILEGE');
INSERT INTO public.privilege (id, name) VALUES (2, 'READ_PRIVILEGE');
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (1, 1);

INSERT INTO public.users (id, create_date, email, enabled, first_name, last_name, password, token_expired)
VALUES
(1, '2025-04-23T10:00:00', 'user1@example.com', TRUE, 'John', 'Doe', '$2a$12$MmyeeuB0UkKrEjOridpXZ.f9u05MiRHz1GsSWGqwC4wy40kNrrOV.', FALSE),
(2, '2025-04-22T11:00:00', 'user2@example.com', TRUE, 'Jane', 'Smith', 'securePassword45', TRUE),
(3, '2025-04-21T09:30:00', 'user3@example.com', FALSE, 'Robert', 'Brown', 'adminPass321', FALSE),
(4, '2025-04-20T08:15:00', 'user4@example.com', TRUE, 'Alice', 'Johnson', 'alice2025!', TRUE),
(5, '2025-04-19T12:45:00', 'user5@example.com', TRUE, 'Charlie', 'Davis', 'charlie2025!', FALSE);

INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);