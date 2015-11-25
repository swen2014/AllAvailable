drop database if exists allavailable;
create database if not exists allavailable;
use allavailable;
create table user (email varchar(100) not null, password varchar(40) not null, name varchar(100) not null, primary key(email));
create table building (buildingId int auto_increment not null, name varchar(255) not null, primary key(buildingId));
create table room (roomId int auto_increment not null, name varchar(255) not null, capacity int not null, lock_status bool not null, type varchar(255) not null, image_src varchar(255) not null, buildingId int, primary key(roomId), foreign key (buildingId) references building(buildingId) on delete cascade on update cascade);
create table seat (seatId int auto_increment not null, name varchar(255) not null, roomId int, occupied bool not null, primary key(seatId), foreign key (roomId) references room(roomId) on delete cascade on update cascade);
create table reservation (id int auto_increment not null, time varchar(100) not null, date varchar(100) not null, duration double not null, user_email varchar(100) not null, seatId int not null, primary key (id), foreign key (user_email) references user(email) on delete cascade on update cascade, foreign key (seatId) references seat(seatId) on delete cascade on update cascade);
create table comment (commentId int auto_increment not null, content varchar(1024) not null, time varchar(100) not null, date varchar(100) not null, user_email varchar(100) not null, roomId int not null, image_url varchar(1024), primary key (commentId), foreign key (user_email) references user(email) on delete cascade on update cascade, foreign key(roomId) references room(roomId) on delete cascade on update cascade); 

create view reservation_detail as select b.name as building_name, r.name as room_name, r.capacity, r.lock_status, r.type, r.image_src, s.name as seat_name, re.* from building b, room r, seat s, reservation re where b.buildingId=r.buildingId and r.roomId=s.roomId and re.seatId=s.seatId;