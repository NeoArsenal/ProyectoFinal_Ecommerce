-- 1. Insertar ROLES
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO roles (nombre) VALUES ('ROLE_USER');

-- 2. Insertar un USUARIO de prueba
INSERT INTO usuarios (email, password) VALUES ('cliente@prueba.com', 'test1234');

-- 3. Asignar ROLES a Usuarios (Usando subconsultas en cada insert individual)
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES ((SELECT id FROM usuarios WHERE email = 'n00230005@upn.pe'), (SELECT id FROM roles WHERE nombre = 'ROLE_ADMIN'));
INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES ((SELECT id FROM usuarios WHERE email = 'cliente@prueba.com'), (SELECT id FROM roles WHERE nombre = 'ROLE_USER'));

-- 4. Insertar DETALLES_USUARIO
INSERT INTO detalles_usuario (direccion, dni, telefono, usuario_id) VALUES ('Av. Siempre Viva 742', '87654321', '999888777', (SELECT id FROM usuarios WHERE email = 'cliente@prueba.com'));

-- 5. Insertar CATEGORIAS (Separadas individualmente)
INSERT INTO categorias (nombre, descripcion) VALUES ('Electrónica', 'Dispositivos, laptops y celulares');
INSERT INTO categorias (nombre, descripcion) VALUES ('Ropa', 'Prendas de vestir para caballero y dama');

-- 6. Insertar ALMACENES (Separados individualmente)
INSERT INTO almacenes (nombre, ubicacion) VALUES ('Almacén Principal', 'Zona Industrial Callao');
INSERT INTO almacenes (nombre, ubicacion) VALUES ('Tienda Centro', 'Av. Arequipa 123');

-- 7. Insertar PROVEEDORES (Separados individualmente)
INSERT INTO proveedores (empresa, contacto, telefono) VALUES ('Tech Solutions SAC', 'Juan Perez', '01-555-1234');
INSERT INTO proveedores (empresa, contacto, telefono) VALUES ('Moda Textil EIRL', 'Maria Gomez', '01-555-5678');

-- 8. Insertar METODOS_ENVIO (Separados individualmente)
INSERT INTO metodos_envio (nombre) VALUES ('Envío Express');
INSERT INTO metodos_envio (nombre) VALUES ('Envío Standard');

-- 9. Insertar PRODUCTOS (Individuales con subconsultas para la FK)
INSERT INTO productos (nombre, descripcion, precio, stock, categoria_id) VALUES ('Laptop Gamer', 'Laptop de alto rendimiento 16GB RAM', 3500.00, 10, (SELECT id FROM categorias WHERE nombre = 'Electrónica'));
INSERT INTO productos (nombre, descripcion, precio, stock, categoria_id) VALUES ('Smartphone 5G', 'Teléfono inteligente 128GB', 1200.00, 25, (SELECT id FROM categorias WHERE nombre = 'Electrónica'));
INSERT INTO productos (nombre, descripcion, precio, stock, categoria_id) VALUES ('Camiseta Algodón', 'Camiseta básica talla M', 45.90, 100, (SELECT id FROM categorias WHERE nombre = 'Ropa'));

-- 10. Insertar RELACIÓN PRODUCTO_PROVEEDORES (Individuales)
INSERT INTO producto_proveedores (producto_id, proveedor_id) VALUES ((SELECT id FROM productos WHERE nombre = 'Laptop Gamer'), (SELECT id FROM proveedores WHERE empresa = 'Tech Solutions SAC'));
INSERT INTO producto_proveedores (producto_id, proveedor_id) VALUES ((SELECT id FROM productos WHERE nombre = 'Camiseta Algodón'), (SELECT id FROM proveedores WHERE empresa = 'Moda Textil EIRL'));

-- 11. Insertar INVENTARIOS (Individuales)
INSERT INTO inventarios (cantidad, almacen_id, producto_id) VALUES (5, (SELECT id FROM almacenes WHERE nombre = 'Almacén Principal'), (SELECT id FROM productos WHERE nombre = 'Laptop Gamer'));
INSERT INTO inventarios (cantidad, almacen_id, producto_id) VALUES (50, (SELECT id FROM almacenes WHERE nombre = 'Tienda Centro'), (SELECT id FROM productos WHERE nombre = 'Camiseta Algodón'));

-- 12. Insertar un PEDIDO
INSERT INTO pedidos (fecha, total, usuario_id) VALUES (NOW(), 3545.90, (SELECT id FROM usuarios WHERE email = 'cliente@prueba.com'));

-- 13. Insertar DETALLES_PEDIDO (Individuales)
INSERT INTO detalles_pedido (cantidad, precio_unitario, pedido_id, producto_id) VALUES (1, 3500.00, (SELECT id FROM pedidos WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'cliente@prueba.com') ORDER BY id DESC LIMIT 1), (SELECT id FROM productos WHERE nombre = 'Laptop Gamer'));
INSERT INTO detalles_pedido (cantidad, precio_unitario, pedido_id, producto_id) VALUES (1, 45.90, (SELECT id FROM pedidos WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'cliente@prueba.com') ORDER BY id DESC LIMIT 1), (SELECT id FROM productos WHERE nombre = 'Camiseta Algodón'));

-- 14. Insertar PAGO
INSERT INTO pagos (fecha_pago, metodo_pago, pedido_id) VALUES (NOW(), 'Tarjeta de Crédito', (SELECT id FROM pedidos WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'cliente@prueba.com') ORDER BY id DESC LIMIT 1));

-- 15. Insertar ENVIO
INSERT INTO envios (estado, numero_tracking, metodo_envio_id, pedido_id) VALUES ('En Preparación', 'TRACK-PE-001', (SELECT id FROM metodos_envio WHERE nombre = 'Envío Express'), (SELECT id FROM pedidos WHERE usuario_id = (SELECT id FROM usuarios WHERE email = 'cliente@prueba.com') ORDER BY id DESC LIMIT 1));