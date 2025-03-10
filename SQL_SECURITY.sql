create database security_basic;
go 
use  security_basic;
go

create table users(
id int identity primary key,
vai_tro_id int,
[user_name] nvarchar(20),
[password] nvarchar(20),
trang_thai int
foreign key (vai_tro_id) references roles(id)
)

create table roles(
id int identity primary key,
ma_role nvarchar(20),
ten_role nvarchar(20),
trang_thai int
)

insert into roles values(N'ADMIN', N'admin',1),
(N'USER', N'user',1),
(N'GUEST', N'guest',1)

insert into users values(1,N'ducanhcao11','12345',1),(2,N'caothinga02','123456',1),(1,N'caothithanh06','1234567',1)