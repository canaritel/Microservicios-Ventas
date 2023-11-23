--DROP TABLE IF EXISTS tbl_products;
--DROP TABLE IF EXISTS tbl_categories;

--CREATE TABLE IF NOT EXISTS tbl_categories (
--  id BIGINT NOT NULL PRIMARY KEY,
--  name VARCHAR(255) NOT NULL
--);

--CREATE TABLE IF NOT EXISTS tbl_products (
--  id BIGINT NOT NULL PRIMARY KEY,
--  name VARCHAR(255) NOT NULL,
--  description VARCHAR(255),
--  stock DOUBLE NOT NULL,
--  price DOUBLE NOT NULL,
--  status VARCHAR(255) NOT NULL,
--  create_at TIMESTAMP NOT NULL,
--  category_id BIGINT NOT NULL,
--  FOREIGN KEY (category_id) REFERENCES tbl_categories(id)
--);
