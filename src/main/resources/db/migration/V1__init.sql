CREATE TABLE member (
                        member_id BIGINT NOT NULL AUTO_INCREMENT,
                        created_at VARCHAR(255),
                        updated_at VARCHAR(255),
                        city VARCHAR(255),
                        street VARCHAR(255),
                        zipcode VARCHAR(255),
                        authority VARCHAR(255),
                        name VARCHAR(255),
                        password VARCHAR(255),
                        profile_image_url VARCHAR(255),
                        username VARCHAR(255),
                        favorite INTEGER NOT NULL,
                        PRIMARY KEY (member_id)
) engine=InnoDB;

CREATE TABLE `order` (
                         order_id BIGINT NOT NULL AUTO_INCREMENT,
                         created_at VARCHAR(255),
                         updated_at VARCHAR(255),
                         city VARCHAR(255),
                         street VARCHAR(255),
                         zipcode VARCHAR(255),
                         date DATETIME(6),
                         delivery_status VARCHAR(255),
                         order_status VARCHAR(255),
                         member_id BIGINT NOT NULL,
                         PRIMARY KEY (order_id)
) engine=InnoDB;

CREATE TABLE order_item (
                            order_item_id BIGINT NOT NULL AUTO_INCREMENT,
                            created_at VARCHAR(255),
                            updated_at VARCHAR(255),
                            count INTEGER NOT NULL,
                            order_price INTEGER NOT NULL,
                            item_id BIGINT NOT NULL,
                            order_id BIGINT NOT NULL,
                            PRIMARY KEY (order_item_id)
) engine=InnoDB;

CREATE TABLE item (
                      item_id BIGINT NOT NULL AUTO_INCREMENT,
                      created_at VARCHAR(255),
                      updated_at VARCHAR(255),
                      name VARCHAR(255),
                      price INTEGER NOT NULL,
                      stock_quantity INTEGER NOT NULL,
                      PRIMARY KEY (item_id)
) engine=InnoDB;

ALTER TABLE `order`
    ADD CONSTRAINT FKbtfnkke0l8kyq7lyhpwjtg5ev
        FOREIGN KEY (member_id)
            REFERENCES member (member_id);

ALTER TABLE order_item
    ADD CONSTRAINT FKs234mi6jususbx4b37k44cipy
        FOREIGN KEY (order_id)
            REFERENCES `order` (order_id);

ALTER TABLE order_item
    ADD CONSTRAINT FKija6hjjiit8dprnmvtvgdp6ru
        FOREIGN KEY (item_id)
            REFERENCES item (item_id);