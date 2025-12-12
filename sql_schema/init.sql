-- 1. Очищення (видаляємо в зворотному порядку через зв'язки)
DROP TABLE IF EXISTS details;
DROP TABLE IF EXISTS components;
DROP TABLE IF EXISTS products;

-- 2. Таблиця ВИРОБИ (Products) - Корінь
CREATE TABLE products (
    id uuid PRIMARY KEY,
    name text NOT NULL,
    serial_number text NOT NULL,
    category text NOT NULL,
    CONSTRAINT uq_products_serial UNIQUE (serial_number)
);

-- 3. Таблиця ВУЗЛИ (Components) - Посилається на products
CREATE TABLE components (
    id serial PRIMARY KEY,
    name text NOT NULL,
    description text,
    product_id uuid NOT NULL,
    CONSTRAINT fk_components_product FOREIGN KEY (product_id) 
        REFERENCES products (id) ON DELETE CASCADE
);

-- 4. Таблиця ДЕТАЛІ (Details) - Посилається на components
CREATE TABLE details (
    id serial PRIMARY KEY,
    name text NOT NULL,
    material text,
    quantity integer DEFAULT 0, -- 👇 НОВЕ ПОЛЕ (Кількість)
    component_id integer NOT NULL,
    CONSTRAINT fk_details_component FOREIGN KEY (component_id) 
        REFERENCES components (id) ON DELETE CASCADE
);

-- 1. Додаємо Вироби
INSERT INTO products (id, name, serial_number, category) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Електродвигун АІР-100', 'PR-1001', 'Двигуни'),
    ('22222222-2222-2222-2222-222222222222', 'Генератор G-500', 'PR-1002', 'Генератори');

-- 2. Додаємо Вузли
INSERT INTO components (name, description, product_id) VALUES
    -- Вузли для Електродвигуна
    ('Статор', 'Нерухома частина', '11111111-1111-1111-1111-111111111111'), -- id=1
    ('Ротор', 'Рухома частина', '11111111-1111-1111-1111-111111111111'),   -- id=2
    
    -- Вузли для Генератора
    ('Паливна система', 'Подача палива', '22222222-2222-2222-2222-222222222222'); -- id=3

-- 3. Додаємо Деталі (З урахуванням кількості)
INSERT INTO details (name, material, quantity, component_id) VALUES
    -- Деталі для Статора (id=1)
    ('Обмотка мідна', 'Мідь', 50, 1),
    ('Корпус статора', 'Чавун', 1, 1),

    -- Деталі для Ротора (id=2)
    ('Вал ротора', 'Сталь 45', 1, 2),
    ('Підшипник', 'Сталь ШХ15', 2, 2),

    -- Деталі для Паливної системи (id=3)
    ('Насос високого тиску', 'Алюміній', 1, 3);
    
    -- 4. Таблиця Користувачів
CREATE TABLE users (
    id serial PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- тут довгий хеш BCrypt
    role VARCHAR(50) NOT NULL
);

INSERT INTO users (username, password, role) VALUES 
('admin', '$2a$10$eez6w4xItMkUR0HQm8nY0eXt/lr72KnKdlw4.ojuPm9MS1MGRGQIK', 'ADMIN'), -- пароль: admin
('user',  '$2a$10$mSDJkolw5QlSUgHixHbzYe0dGv2h2QIFXFpm6tqv63azlHXDF8iUe', 'USER');  -- пароль: user