DROP TABLE IF EXISTS RESTAURANT;


CREATE TABLE RESTAURANT
(
    id                   VARCHAR(36) PRIMARY KEY,
    name                 VARCHAR(250) NOT NULL,
    type                 VARCHAR(100) NOT NULL,
    image                VARCHAR(MAX) NOT NULL,
    radius               DECIMAL(60000,2) NOT NULL,
    x_axis               FLOAT NOT NULL,
    y_axis               FLOAT NOT NULL,
    opening_hours        VARCHAR(100) NOT NULL

);

