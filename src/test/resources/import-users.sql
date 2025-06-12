-- ========================
-- ROLES
-- ========================
INSERT INTO public.role (id, name) VALUES (1, 'USER');
INSERT INTO public.role (id, name) VALUES (2, 'ADMIN');

-- ========================
-- PRIVILEGIOS
-- ========================
INSERT INTO public.privilege (id, name) VALUES (1, 'WRITE_PRIVILEGE');
INSERT INTO public.privilege (id, name) VALUES (2, 'READ_PRIVILEGE');

-- ========================
-- ASOCIACIÓN ROL - PRIVILEGIO
-- ========================
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (1, 1); -- USER → WRITE
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (2, 1); -- ADMIN → WRITE
INSERT INTO public.role_privilege (role_id, privilege_id) VALUES (2, 2); -- ADMIN → READ

-- ========================
-- USUARIOS (IDs 1 al 6)
-- ========================
INSERT INTO public.users (
    id, create_date, email, enabled, first_name, last_name, password, token_expired
) VALUES
-- 5 USERS
(1, '2025-06-01T09:30:00', 'carlos.mendez@gmail.com', TRUE, 'Carlos', 'Méndez',
 '$2a$12$MmyeeuB0UkKrEjOridpXZ.f9u05MiRHz1GsSWGqwC4wy40kNrrOV.', FALSE),

(2, '2025-06-02T10:15:00', 'laura.fernandez@hotmail.com', TRUE, 'Laura', 'Fernández',
 '$2a$12$MmyeeuB0UkKrEjOridpXZ.f9u05MiRHz1GsSWGqwC4wy40kNrrOV.', FALSE),

(3, '2025-06-03T11:45:00', 'mario.rios@yahoo.com', TRUE, 'Mario', 'Ríos',
 '$2a$12$MmyeeuB0UkKrEjOridpXZ.f9u05MiRHz1GsSWGqwC4wy40kNrrOV.', FALSE),

(4, '2025-06-04T08:00:00', 'ana.morales@outlook.com', TRUE, 'Ana', 'Morales',
 '$2a$12$MmyeeuB0UkKrEjOridpXZ.f9u05MiRHz1GsSWGqwC4wy40kNrrOV.', FALSE),

(5, '2025-06-05T15:25:00', 'jose.rojas@gmail.com', TRUE, 'José', 'Rojas',
 '$2a$12$MmyeeuB0UkKrEjOridpXZ.f9u05MiRHz1GsSWGqwC4wy40kNrrOV.', FALSE),

-- 1 ADMIN
(6, '2025-06-06T12:30:00', 'admin@oportu.com', TRUE, 'Admin', 'Principal',
 '$2a$12$MmyeeuB0UkKrEjOridpXZ.f9u05MiRHz1GsSWGqwC4wy40kNrrOV.', FALSE);

-- ========================
-- ASOCIACIÓN USER → ROL
-- ========================
-- 5 USERS (IDs 1–5)
INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (5, 1);

-- 1 ADMIN (ID 6)
INSERT INTO public.user_role (user_id, role_id) VALUES (6, 2);