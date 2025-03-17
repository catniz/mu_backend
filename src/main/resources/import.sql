-- brand init
insert into brand(id, name) values (1, 'A');
insert into brand(id, name) values (2, 'B');
insert into brand(id, name) values (3, 'C');
insert into brand(id, name) values (4, 'D');
insert into brand(id, name) values (5, 'E');
insert into brand(id, name) values (6, 'F');
insert into brand(id, name) values (7, 'G');
insert into brand(id, name) values (8, 'H');
insert into brand(id, name) values (9, 'I');
ALTER TABLE brand ALTER COLUMN id RESTART WITH 10;

-- product
insert into product(brand_id, category, price) values (1, 'TOP', 11200);
insert into product(brand_id, category, price) values (1, 'OUTER', 5500);
insert into product(brand_id, category, price) values (1, 'PANTS', 4200);
insert into product(brand_id, category, price) values (1, 'SNEAKERS', 9000);
insert into product(brand_id, category, price) values (1, 'BAG', 2000);
insert into product(brand_id, category, price) values (1, 'HAT', 1700);
insert into product(brand_id, category, price) values (1, 'SOCKS', 1800);
insert into product(brand_id, category, price) values (1, 'ACCESSORY', 2300);

insert into product(brand_id, category, price) values (2, 'TOP', 10500);
insert into product(brand_id, category, price) values (2, 'OUTER', 5900);
insert into product(brand_id, category, price) values (2, 'PANTS', 3800);
insert into product(brand_id, category, price) values (2, 'SNEAKERS', 9100);
insert into product(brand_id, category, price) values (2, 'BAG', 2100);
insert into product(brand_id, category, price) values (2, 'HAT', 2000);
insert into product(brand_id, category, price) values (2, 'SOCKS', 2000);
insert into product(brand_id, category, price) values (2, 'ACCESSORY', 2200);

insert into product(brand_id, category, price) values (3, 'TOP', 10000);
insert into product(brand_id, category, price) values (3, 'OUTER', 6200);
insert into product(brand_id, category, price) values (3, 'PANTS', 3300);
insert into product(brand_id, category, price) values (3, 'SNEAKERS', 9200);
insert into product(brand_id, category, price) values (3, 'BAG', 2200);
insert into product(brand_id, category, price) values (3, 'HAT', 1900);
insert into product(brand_id, category, price) values (3, 'SOCKS', 2200);
insert into product(brand_id, category, price) values (3, 'ACCESSORY', 2100);

insert into product(brand_id, category, price) values (4, 'TOP', 10100);
insert into product(brand_id, category, price) values (4, 'OUTER', 5100);
insert into product(brand_id, category, price) values (4, 'PANTS', 3000);
insert into product(brand_id, category, price) values (4, 'SNEAKERS', 9500);
insert into product(brand_id, category, price) values (4, 'BAG', 2500);
insert into product(brand_id, category, price) values (4, 'HAT', 1500);
insert into product(brand_id, category, price) values (4, 'SOCKS', 2400);
insert into product(brand_id, category, price) values (4, 'ACCESSORY', 2000);

insert into product(brand_id, category, price) values (5, 'TOP', 10700);
insert into product(brand_id, category, price) values (5, 'OUTER', 5000);
insert into product(brand_id, category, price) values (5, 'PANTS', 3800);
insert into product(brand_id, category, price) values (5, 'SNEAKERS', 9900);
insert into product(brand_id, category, price) values (5, 'BAG', 2300);
insert into product(brand_id, category, price) values (5, 'HAT', 1800);
insert into product(brand_id, category, price) values (5, 'SOCKS', 2100);
insert into product(brand_id, category, price) values (5, 'ACCESSORY', 2100);

insert into product(brand_id, category, price) values (6, 'TOP', 11200);
insert into product(brand_id, category, price) values (6, 'OUTER', 7200);
insert into product(brand_id, category, price) values (6, 'PANTS', 4000);
insert into product(brand_id, category, price) values (6, 'SNEAKERS', 9300);
insert into product(brand_id, category, price) values (6, 'BAG', 2100);
insert into product(brand_id, category, price) values (6, 'HAT', 1600);
insert into product(brand_id, category, price) values (6, 'SOCKS', 2300);
insert into product(brand_id, category, price) values (6, 'ACCESSORY', 1900);

insert into product(brand_id, category, price) values (7, 'TOP', 10500);
insert into product(brand_id, category, price) values (7, 'OUTER', 5800);
insert into product(brand_id, category, price) values (7, 'PANTS', 3900);
insert into product(brand_id, category, price) values (7, 'SNEAKERS', 9000);
insert into product(brand_id, category, price) values (7, 'BAG', 2200);
insert into product(brand_id, category, price) values (7, 'HAT', 1700);
insert into product(brand_id, category, price) values (7, 'SOCKS', 2100);
insert into product(brand_id, category, price) values (7, 'ACCESSORY', 2000);

insert into product(brand_id, category, price) values (8, 'TOP', 10800);
insert into product(brand_id, category, price) values (8, 'OUTER', 6300);
insert into product(brand_id, category, price) values (8, 'PANTS', 3100);
insert into product(brand_id, category, price) values (8, 'SNEAKERS', 9700);
insert into product(brand_id, category, price) values (8, 'BAG', 2100);
insert into product(brand_id, category, price) values (8, 'HAT', 1600);
insert into product(brand_id, category, price) values (8, 'SOCKS', 2000);
insert into product(brand_id, category, price) values (8, 'ACCESSORY', 2000);

insert into product(brand_id, category, price) values (9, 'TOP', 11400);
insert into product(brand_id, category, price) values (9, 'OUTER', 6700);
insert into product(brand_id, category, price) values (9, 'PANTS', 3200);
insert into product(brand_id, category, price) values (9, 'SNEAKERS', 9500);
insert into product(brand_id, category, price) values (9, 'BAG', 2400);
insert into product(brand_id, category, price) values (9, 'HAT', 1700);
insert into product(brand_id, category, price) values (9, 'SOCKS', 1700);
insert into product(brand_id, category, price) values (9, 'ACCESSORY', 2400);
