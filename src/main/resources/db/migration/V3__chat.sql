create table chat_room (
                           room_id bigint not null auto_increment,
                           created_at varchar(255),
                           updated_at varchar(255),
                           name varchar(255),
                           primary key (room_id)
) engine=InnoDB;

create table chat_message (
                              message_id bigint not null auto_increment,
                              content varchar(255),
                              time datetime(6),
                              room_id bigint,
                              member_id bigint,
                              primary key (message_id)
) engine=InnoDB;

alter table chat_message
    add constraint FKynfbnbqot8mpd1tquoc2s1w5
        foreign key (member_id)
            references member (member_id);

alter table chat_message
    add constraint FKfvbc4wvhk51y0qtnjrbminxfu
        foreign key (room_id)
            references chat_room (room_id);