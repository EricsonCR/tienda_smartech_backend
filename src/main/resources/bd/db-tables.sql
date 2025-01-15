create database tienda_smartech;
use tienda_smartech;

drop table if exists roles;
create table roles(
	id int primary key auto_increment,
    nombre varchar(20) unique
);

drop table if exists usuarios;
create table usuarios(
	id int primary key auto_increment,
    rol int,
    documento enum('DNI', 'CEXT'),
    numero varchar(20) unique,
    nombres varchar(50),
    apellidos varchar(50),
    telefono varchar(20),
    direccion varchar(50),
    email varchar(50) unique,
    password varchar(255),
    nacimiento date,
    registro date,
    actualiza date,
    estado bit,
    foreign key (rol)  references roles(id)
    on delete set null
    on update set null
);

drop table if exists categorias;
create table categorias(
	id int primary key auto_increment,
    nombre varchar(20) unique
);

drop table if exists productos;
create table productos(
	id int primary key auto_increment,
    categoria int,
    usuario int,
    nombre varchar(50) unique,
    descripcion text,
    imagen varchar(255),
    precio decimal(6,2),
    stock int,
    registro date,
    actualiza date,
    estado boolean,
    foreign key (categoria) references categorias(id)
    on delete set null on update set null,
    foreign key (usuario) references usuarios(id)
    on delete set null on update set null
);

select * from usuarios;
select * from roles;
select * from categorias order by id;
select * from productos;