CREATE DATABASE IF NOT EXISTS womenshop;
USE womenshop;

DROP TABLE IF EXISTS products;
CREATE TABLE products (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(100),
                              category VARCHAR(100),
                              price DOUBLE,
                              stock INT

);


INSERT INTO products (name, category, price, stock) VALUES
                                                        ('T-Shirt Basic', 'Clothes', 14.99, 50),
                                                        ('Jeans Slim Fit', 'Clothes', 39.99, 25),
                                                        ('Sneakers White', 'Shoes', 59.90, 15),
                                                        ('Leather Handbag', 'Accessories', 89.00, 8),
                                                        ('Summer Dress', 'Clothes', 29.50, 30),
                                                        ('Wool Scarf', 'Accessories', 12.90, 40),
                                                        ('Ankle Boots', 'Shoes', 79.99, 12),
                                                        ('Sports Leggings', 'Clothes', 24.99, 35),
                                                        ('Silver Necklace', 'Jewelry', 49.99, 20),
                                                        ('Sunglasses Black', 'Accessories', 19.99, 28),
                                                        ('High Heels Red', 'Shoes', 69.99, 10),
                                                        ('Backpack Brown', 'Accessories', 34.90, 22),
                                                        ('Perfume 50ml', 'Beauty', 44.99, 18),
                                                        ('Hoodie Oversized', 'Clothes', 32.50, 26),
                                                        ('Earrings Gold', 'Jewelry', 29.99, 50);

