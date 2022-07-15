DROP DATABASE IF EXISTS book_shop;
CREATE DATABASE book_shop;
DROP TABLE  IF EXISTS book_shop.book;
CREATE TABLE book_shop.book (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  edition INT(11),
  price INT(11),
  
  PRIMARY KEY (id)
);