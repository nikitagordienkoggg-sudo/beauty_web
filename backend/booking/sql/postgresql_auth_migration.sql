-- Добавляет поля для авторизации и ролей в users
ALTER TABLE users ADD COLUMN IF NOT EXISTS password VARCHAR(255);
ALTER TABLE users ADD COLUMN IF NOT EXISTS role VARCHAR(32);

-- Заполняет роль и временный пароль для существующих клиентов
UPDATE users u
SET role = 'ROLE_CLIENT',
    password = COALESCE(password, '$2a$10$7EqJtq98hPqEX7fNZaFWoOHiVx1s7h.rYvJZZzKzbwQ6vurIWB9K6')
WHERE role IS NULL
  AND EXISTS (SELECT 1 FROM client c WHERE c.id = u.id);

-- Заполняет роль и временный пароль для существующих мастеров
UPDATE users u
SET role = 'ROLE_MASTER',
    password = COALESCE(password, '$2a$10$7EqJtq98hPqEX7fNZaFWoOHiVx1s7h.rYvJZZzKzbwQ6vurIWB9K6')
WHERE role IS NULL
  AND EXISTS (SELECT 1 FROM master m WHERE m.id = u.id);

ALTER TABLE users ALTER COLUMN password SET NOT NULL;
ALTER TABLE users ALTER COLUMN role SET NOT NULL;
