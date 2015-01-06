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
	CONSTRAINT PASSWORDCARD_FK_ACCOUNT FOREIGN KEY (account) REFERENCES account(id)
);

CREATE TABLE passwordField (
	id INTEGER,
	idCard INTEGER,
	type TEXT(80),
	libelle TEXT,
	value TEXT,
	CONSTRAINT passwordfield_pk PRIMARY KEY (id),
	CONSTRAINT PASSWORDFIELD_FK_PASSWORDCARD FOREIGN KEY (idCard) REFERENCES passwordCard(id)
);

