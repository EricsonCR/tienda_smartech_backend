create database tienda_smartech;
use tienda_smartech;

drop table if exists usuarios;
create table usuarios (
	id_usuario int primary key auto_increment,
    documento enum ('DNI', 'CEXT') default 'DNI',
    numero varchar(20) unique,
    id_rol int,
    nombres varchar(50),
    apellidos varchar(50),
    direccion varchar(50),
    telefono varchar(50),
    email varchar(50) unique,
    password varchar(255),
    nacimiento date,
    actualizacion date,
    estado bit,
    foreign key (id_rol) references roles(id_rol)
    on delete set null
    on update set null
);

drop table if exists roles;
create table roles (
	id_rol int primary key auto_increment,
    nombre varchar(20) unique
);

select * from usuarios;
select * from roles;