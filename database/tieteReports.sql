CREATE TABLE inspetores (
	nome varchar(255),
	cpf varchar(20),
	ra varchar(20),
	cargo varchar(50),
	email varchar(255),
	industria varchar(100),
	hashpass varchar(255),
	salt bytea,
	PRIMARY KEY (ra)
);


SELECT * FROM inspetores;

