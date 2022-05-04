DROP SCHEMA IF EXISTS rentalsystemdbs cascade;

CREATE SCHEMA rentalsystemdbs;

SET SCHEMA 'rentalsystemdbs';

CREATE DOMAIN phone_number as varchar(15);

CREATE TABLE equipment (
	name VARCHAR(255),
	type VARCHAR(255),
	availability BOOLEAN,
	price DECIMAL(4,2),
	PRIMARY KEY (name, type)
);

CREATE TABLE manager(
	email VARCHAR(255) PRIMARY KEY,
	phone_number phone_number,
	first_name VARCHAR(255),
	last_name VARCHAR(255)
);

CREATE TABLE rentee(
	email VARCHAR(255) PRIMARY KEY,
	phone_number phone_number,
	first_name VARCHAR(255),
	last_name VARCHAR(255),
	apartment_no SMALLINT
);

CREATE TABLE reservation(
	reservation_id serial PRIMARY KEY,
	date TIMESTAMP,
	approval BOOLEAN,
	used_by VARCHAR(255),
	equipment_name VARCHAR(255),
	equipment_type VARCHAR(255),
	FOREIGN KEY (equipment_name, equipment_type) references equipment(name, type),
	FOREIGN KEY (used_by) references rentee(email)
);

