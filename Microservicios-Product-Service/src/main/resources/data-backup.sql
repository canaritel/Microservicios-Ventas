-- Verifica si el registro con id 1 no existe antes de insertarlo
INSERT INTO tbl_categories (id, name)
SELECT 1, 'shoes'
WHERE NOT EXISTS (SELECT 1 FROM tbl_categories WHERE id = 1);

-- Verifica si el registro con id 2 no existe antes de insertarlo
INSERT INTO tbl_categories (id, name)
SELECT 2, 'books'
WHERE NOT EXISTS (SELECT 1 FROM tbl_categories WHERE id = 2);

-- Verifica si el registro con id 3 no existe antes de insertarlo
INSERT INTO tbl_categories (id, name)
SELECT 3, 'electronics'
WHERE NOT EXISTS (SELECT 1 FROM tbl_categories WHERE id = 3);

-- Elimina los registros con los IDs 1, 2 y 3 de tbl_products si existen
DELETE FROM tbl_products
WHERE id IN (
  SELECT id
  FROM tbl_products
  WHERE id IN (1, 2, 3)
  AND id IS NOT NULL
);

INSERT INTO tbl_products (id, name, description, stock, price, status, create_at, category_id)
VALUES (1, 'Spring Boot in Action', 'Craig Walls is a software developer at Pivotal and is the author of Spring in Action', 12.0, 40.06, 'CREATED', '2018-09-05T00:00:00', 2);

INSERT INTO tbl_products (id, name, description, stock, price, status, create_at, category_id)
VALUES (2, 'adidas Cloudfoam Ultimate', 'Walk in the air in the black / black CLOUDFOAM ULTIMATE running shoe from ADIDAS', 5.0, 178.89, 'CREATED', '2018-09-05T00:00:00', 1);

INSERT INTO tbl_products (id, name, description, stock, price, status, create_at, category_id)
VALUES (3, 'under armour Mens Micro G Assert – 7', 'under armour Mens Lightweight mesh upper delivers complete breathability . Durable leather overlays for stability', 4.0, 12.5, 'CREATED', '2018-09-05T00:00:00', 1);
