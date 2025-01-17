create database tienda_smartech;
use tienda_smartech;

drop table if exists usuarios;
create table usuarios(
	id int primary key auto_increment,
    rol enum('ADMIN','USER','CLIENTE','INVITED'),
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
    estado bit
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

drop table if exists ventas;
create table ventas(
	id int primary key auto_increment,
    usuario int,
    total decimal(6,2),
    descuento decimal(6,2),
    impuesto decimal(6,2),
    metodo enum('EFECTIVO', 'BCP','INTERBANK', 'BBVA', 'YAPE', 'PLIN'),
    estado enum('PENDIENTE','PAGADO','ANULADO'),
    comentarios varchar(255),
    registro date,
    actualiza date,
    foreign key (usuario) references usuarios(id)
    on delete set null on update set null
);

drop table if exists venta_detalles;
create table venta_detalles(
	id int primary key auto_increment,
    venta int,
    producto int,
    cantidad int,
    precio decimal(6,2),
    total decimal(6,2),
    descuento decimal(6,2),
    impuesto decimal(6,2),
    comentarios varchar(50),
    medida enum('UND','DOC','BLS','CAJ','PQT','CTO', 'MIL'),
    foreign key (venta) references ventas(id)
    on delete set null on update set null,
    foreign key (producto) references productos(id)
    on delete set null on update set null
);

/*
use test;
drop database tienda_smartech;

truncate table ventas;
truncate table venta_detalles;
truncate table clientes;

delete from ventas where cliente = 2;
delete from clientes where id = 2;

set foreign_key_checks = 0;
drop table if exists ventas;
set foreign_key_checks = 1;

update categorias
set nombre = 'ram'
where id = 2;

*/

select * from usuarios;
select * from categorias order by id;
select * from productos;
select * from ventas;
select * from venta_detalles;
