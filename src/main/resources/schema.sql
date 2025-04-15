CREATE TABLE paint (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    red INT NOT NULL CHECK (red BETWEEN 0 AND 255),
    green INT NOT NULL CHECK (green BETWEEN 0 AND 255),
    blue INT NOT NULL CHECK (blue BETWEEN 0 AND 255),
    type VARCHAR(50) NOT NULL,         -- 例：アクリル、水性など
    amount INT NOT NULL DEFAULT 0,     -- 単位はml前提（拡張可能）
    category VARCHAR(50)               -- 例：赤系、緑系、青系など
);
