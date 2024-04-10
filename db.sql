drop database if exists lenguajes;
create database lenguajes;
use lenguajes;

create table tipo(
    id int not null auto_increment,
    nombre varchar(30) not null,
    descripcion varchar(100),
    constraint tipo_pk primary key(id)
);
 
create table lenguaje(
    id int not null auto_increment,
    nombre varchar(30) not null,
    id_tipo int not null,
    caracteristicas varchar(100),
    constraint lenguaje_pk primary key(id),
    constraint tipo_fk foreign key(id_tipo) references tipo(id)
);  
 
 
INSERT INTO tipo(nombre, descripcion) values ('Compilado', 'Se ejecuta directamente como lenguaje máquina');
 
 
INSERT INTO tipo(nombre, descripcion) values ('Semi-compilado', 'El compilador crea código de tres direcciones');
 
INSERT INTO tipo(nombre, descripcion) values ('Interpretado', 'Se interpreta el código fuente en cada ejecución');
 
 
INSERT INTO lenguaje(nombre, id_tipo, caracteristicas) VALUES ('C',1,'Lenguaje estructurado fuertemente tipado y de tipado estático');
 
INSERT INTO lenguaje(nombre, id_tipo, caracteristicas) VALUES ('Java',2,'Lenguaje orientado a objetos fuertemente tipado y de tipado estático');
 
INSERT INTO lenguaje(nombre, id_tipo, caracteristicas) VALUES ('Python',3,'Lenguaje orientado a objetos fuertemente tipado y de tipado dinámico');
 
INSERT INTO lenguaje(nombre, id_tipo, caracteristicas) VALUES ('PHP',3,'Lenguaje orientado a objetos débilmente tipado y de tipado dinámico');


