create table notification (
                              notification_id bigint not null auto_increment,
                              created_at varchar(255),
                              updated_at varchar(255),
                              category varchar(255),
                              content varchar(255),
                              date datetime(6),
                              receiver_id bigint,
                              sender_id bigint,
                              primary key (notification_id)
) engine=InnoDB;

alter table notification
    add constraint FK1jpw68rbaxvu8u5l1dniain1l
        foreign key (receiver_id)
            references member (member_id);

alter table notification
    add constraint FKovuqht5f5v592yehlp3tgji9y
        foreign key (sender_id)
            references member (member_id);