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
```