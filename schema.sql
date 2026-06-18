-- 1. Crear base de datos (ajustá el nombre a tu preferencia)
CREATE DATABASE IF NOT EXISTS food_store_db;
USE food_store_db;

-- 2. Tabla: categorias (hereda de Base)
CREATE TABLE categorias (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

-- 3. Tabla: productos (hereda de Base, FK a categorias)
CREATE TABLE productos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK (precio >= 0),
    descripcion TEXT,
    stock INT NOT NULL CHECK (stock >= 0),
    imagen VARCHAR(255),
    disponible BOOLEAN DEFAULT TRUE,
    id_categoria BIGINT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id) ON DELETE RESTRICT
);

-- 4. Tabla: usuarios (hereda de Base)
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    mail VARCHAR(150) NOT NULL UNIQUE,
    celular VARCHAR(20),
    contrasenia VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL DEFAULT 'USUARIO'  -- ADMIN, USUARIO
);

-- 5. Tabla: pedidos (hereda de Base, FK a usuarios)
CREATE TABLE pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE', -- PENDIENTE, CONFIRMADO, TERMINADO, CANCELADO
    total DECIMAL(12,2) NOT NULL DEFAULT 0,
    forma_pago VARCHAR(20) NOT NULL DEFAULT 'EFECTIVO', -- TARJETA, TRANSFERENCIA, EFECTIVO
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE RESTRICT
);

-- 6. Tabla: detalles_pedido (hereda de Base, FK a pedido y producto)
CREATE TABLE detalles_pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    subtotal DECIMAL(12,2) NOT NULL,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id) ON DELETE RESTRICT
);

-- 7. (Opcional) Datos de prueba para empezar a trabajar
INSERT INTO categorias (nombre, descripcion) VALUES 
('Bebidas', 'Bebidas frías y calientes'),
('Comidas', 'Platos principales y acompañamientos'),
('Postres', 'Dulces y tentempiés');

INSERT INTO productos (nombre, precio, descripcion, stock, imagen, disponible, id_categoria) VALUES
('Café americano', 1500, 'Café negro de especialidad', 100, 'cafe.jpg', TRUE, 1),
('Hamburguesa simple', 4500, 'Carne, lechuga, tomate y queso', 50, 'burger.jpg', TRUE, 2),
('Tarta de manzana', 2800, 'Tarta casera con canela', 30, 'tarta.jpg', TRUE, 3);

INSERT INTO usuarios (nombre, apellido, mail, celular, contrasenia, rol) VALUES
('Admin', 'Sistema', 'admin@foodstore.com', '123456789', 'admin123', 'ADMIN'),
('Juan', 'Pérez', 'juan@mail.com', '987654321', 'juan123', 'USUARIO');
