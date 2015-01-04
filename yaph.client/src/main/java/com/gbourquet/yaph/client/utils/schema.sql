CREATE TABLE account (
	id INTEGER,
	nom TEXT,
	prenom TEXT,
	login TEXT,
	password TEXT,
	dateActivation TIMESTAMP,
	dateDesactivation TIMESTAMP,
	CONSTRAINT ACCOUNT_PK PRIMARY KEY (id)
);
CREATE TABLE passwordCard (
	id INTEGER,
	adresse TEXT(250),
	user TEXT,
	password TEXT,
	account INTEGER,
	titre TEXT,
	CONSTRAINT passwordcard_pk PRIMARY KEY (id),
	CONSTRAINT PASSWORDCARD_FK_ACCOUNT FOREIGN KEY (id) REFERENCES account(id)
);
