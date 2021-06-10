# itcluster-java11

# How to create DB

```
CREATE TABLE Points ( 
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` text,
    `longitude` varchar(10),
    `latitude` varchar(10),
    PRIMARY KEY (`id`)
);


CREATE TABLE Category ( 
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `description` text,
    PRIMARY KEY (`id`)
);

CREATE TABLE PointToCategory ( 
    `point_id` int NOT NULL ,
    `category_id` int NOT NULL ,
    FOREIGN KEY (point_id) REFERENCES Points (id),
    FOREIGN KEY (category_id) REFERENCES Category (id)
    
);

CREATE TABLE SearchConfig( 
    `id` int NOT NULL AUTO_INCREMENT,
    `radius` varchar(100),
    `lng` varchar(10),
    `lat` varchar(10),
    `userId` int,
    PRIMARY KEY (`id`)
);

CREATE TABLE SearchConfigToCategory ( 
    `searchConfig_id` int NOT NULL ,
    `category_id` int NOT NULL ,
    FOREIGN KEY (searchConfig_id) REFERENCES  SearchConfig (id),
    FOREIGN KEY (category_id) REFERENCES Category (id)  
);



INSERT INTO Category (name, description) VALUES ("пам'ятка", "предмет духовної або матеріальної культури минулого, унікальний об'єкт природи чи цивілізації, який становить наукову, пізнавальну та естетичну цінність.");
INSERT INTO Category (name, description) VALUES ("парк", "спеціальна обмежена природна або штучна територія, виділена переважно з метою рекреації, відпочинку.");
INSERT INTO Category (name, description) VALUES ("бар", "розважальний заклад, здебільшого, місце продажу напоїв на розлив, частіше алкогольних.");
INSERT INTO Category (name, description) VALUES ("Івано-Франківськ", "місто");

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Парк ім. Т. Шевченка", "Парк з озером де ви можете покататись на велосипеді, скуштувати справжнього морозива, а також покататись на колесі огляду. Для дітей безліч атракціонів і каруселей, а перекусити можна в кафе.", "48.9105971", "24.6855926");
INSERT INTO PointToCategory (point_id, category_id) VALUES (1, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (1, 2);
INSERT INTO PointToCategory (point_id, category_id) VALUES (1, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Меморіальный сквер", "Відмінний парк, де можна посидіти і розслабитися.","48.9170821", "24.7241559");
INSERT INTO PointToCategory (point_id, category_id) VALUES (2, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (2, 2);
INSERT INTO PointToCategory (point_id, category_id) VALUES (2, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Сквер на Валах", "затишний парк", "48.9250291", "24.7123864");
INSERT INTO PointToCategory (point_id, category_id) VALUES (3, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (3, 2);
INSERT INTO PointToCategory (point_id, category_id) VALUES (3, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Площа Міцкевича", "тихе і затишне місце", "48.921418", "24.7089518");
INSERT INTO PointToCategory (point_id, category_id) VALUES (4, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (4, 2);
INSERT INTO PointToCategory (point_id, category_id) VALUES (4, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Алеї біля драмтеатру", "ідеально для прогулянок", "48.9295626", "24.7077128");
INSERT INTO PointToCategory (point_id, category_id) VALUES (5, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (5, 2);
INSERT INTO PointToCategory (point_id, category_id) VALUES (5, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Фортечна галерея «Бастіон»", "Дуже цікаве місце, можна перекусити, відвідати сувенірні магазинчики і насолодиться виставкою художників і скульпторів", "48.9222395", "24.7054383");
INSERT INTO PointToCategory (point_id, category_id) VALUES (6, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (6, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Дем'янів Лаз", "історичне місце", "48.9457215", "24.6799569");
INSERT INTO PointToCategory (point_id, category_id) VALUES (7, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (7, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Галицька Брама", "історичне місце", "48.9229618", "24.7075654");
INSERT INTO PointToCategory (point_id, category_id) VALUES (8, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (8, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Івано-Франківська ратуша", "Музей культурної спадщини", "48.9246836", "24.6953388");
INSERT INTO PointToCategory (point_id, category_id) VALUES (9, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (9, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Пластунам, що не зламали своїх присяг", "історичне місце", "48.9208751", "24.7086626");
INSERT INTO PointToCategory (point_id, category_id) VALUES (10, 1);
INSERT INTO PointToCategory (point_id, category_id) VALUES (10, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Copper Head. Beer workshop", "пивний бар", "48.9211822", "24.7044339");
INSERT INTO PointToCategory (point_id, category_id) VALUES (11, 3);
INSERT INTO PointToCategory (point_id, category_id) VALUES (11, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Paps Beer", "пивний бар", "48.922413", "24.7034412");
INSERT INTO PointToCategory (point_id, category_id) VALUES (12, 3);
INSERT INTO PointToCategory (point_id, category_id) VALUES (12, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Пивна Оселя", "пивний бар", "48.9108305", "24.6973158");
INSERT INTO PointToCategory (point_id, category_id) VALUES (13, 3);
INSERT INTO PointToCategory (point_id, category_id) VALUES (13, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Beer House", "пивний бар", "48.9242125", "24.7354168");
INSERT INTO PointToCategory (point_id, category_id) VALUES (14, 3);
INSERT INTO PointToCategory (point_id, category_id) VALUES (14, 4);

INSERT INTO Points (name, description, latitude, longitude) VALUES ("Живе пиво", "пивний бар", "48.9348634", "24.7432273");
INSERT INTO PointToCategory (point_id, category_id) VALUES (15, 3);
INSERT INTO PointToCategory (point_id, category_id) VALUES (15, 4);



```