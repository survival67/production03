-- 1. Очищення (видаляємо в зворотному порядку через зв'язки)
DROP TABLE IF EXISTS details;
DROP TABLE IF EXISTS components;
DROP TABLE IF EXISTS products;

-- 2. Таблиця ВИРОБИ (Products) - Корінь
CREATE TABLE products (
    id uuid PRIMARY KEY,
    name text NOT NULL,
    serial_number text NOT NULL,
    category text NOT NULL, -- Наприклад: 'Верстат', 'Генератор'
    CONSTRAINT uq_products_serial UNIQUE (serial_number)
);

-- 3. Таблиця ВУЗЛИ (Components) - Посилається на products
CREATE TABLE components (
    id serial PRIMARY KEY,
    name text NOT NULL,
    description text,
    product_id uuid NOT NULL, -- Зовнішній ключ
    CONSTRAINT fk_components_product FOREIGN KEY (product_id) 
        REFERENCES products (id) ON DELETE CASCADE
);

-- 4. Таблиця ДЕТАЛІ (Details) - Посилається на components
CREATE TABLE details (
    id serial PRIMARY KEY,
    name text NOT NULL,
    material text,
    component_id integer NOT NULL, -- Зовнішній ключ
    CONSTRAINT fk_details_component FOREIGN KEY (component_id) 
        REFERENCES components (id) ON DELETE CASCADE
);

-- 1. Додаємо Вироби (запам'ятовуємо ID, щоб використати їх далі)
INSERT INTO products (id, name, serial_number, category) VALUES
    ('11111111-1111-1111-1111-111111111111', 'Електродвигун АІР-100', 'PR-1001', 'Двигуни'),
    ('22222222-2222-2222-2222-222222222222', 'Генератор G-500', 'PR-1002', 'Генератори');

-- 2. Додаємо Вузли (посилаємося на ID виробів вище)
INSERT INTO components (name, description, product_id) VALUES
    -- Вузли для Електродвигуна (ID: 1111...)
    ('Статор', 'Нерухома частина', '11111111-1111-1111-1111-111111111111'), -- id буде 1
    ('Ротор', 'Рухома частина', '11111111-1111-1111-1111-111111111111'),   -- id буде 2
    
    -- Вузли для Генератора (ID: 2222...)
    ('Паливна система', 'Подача палива', '22222222-2222-2222-2222-222222222222'); -- id буде 3

-- 3. Додаємо Деталі (посилаємося на ID вузлів: 1, 2, 3...)
INSERT INTO details (name, material, component_id) VALUES
    -- Деталі для Статора (component_id = 1)
    ('Обмотка мідна', 'Мідь', 1),
    ('Корпус статора', 'Чавун', 1),

    -- Деталі для Ротора (component_id = 2)
    ('Вал ротора', 'Сталь 45', 2),
    ('Підшипник', 'Сталь ШХ15', 2),

    -- Деталі для Паливної системи (component_id = 3)
    ('Насос високого тиску', 'Алюміній', 3);