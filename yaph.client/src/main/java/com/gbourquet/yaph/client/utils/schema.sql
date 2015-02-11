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
	account INTEGER,
	titre TEXT,
	CONSTRAINT passwordCard_pk PRIMARY KEY (id),
	CONSTRAINT passwordCard_FK_account FOREIGN KEY (account) REFERENCES account(id)
);

CREATE TABLE passwordField (
	id INTEGER,
	idCard INTEGER,
	type TEXT(80),
	libelle TEXT,
	value TEXT,
	CONSTRAINT passwordField_pk PRIMARY KEY (id),
	CONSTRAINT passwordField_FK_passwordCard FOREIGN KEY (idCard) REFERENCES passwordCard(id)
);

CREATE TABLE passwordTag (
	id INTEGER,
	idAccount INTEGER,
	libelle TEXT,
	CONSTRAINT passwordTag_pk PRIMARY KEY (id),
	CONSTRAINT passwordGroup_fk_account FOREIGN KEY (idAccount) REFERENCES account(id)
);

CREATE TABLE passwordCardTag (
	idPassword INTEGER,
	idTag INTEGER,
	CONSTRAINT passwordCardTag_pk PRIMARY KEY (idPassword,idTag),
	CONSTRAINT passwordCardTag_fk_passwordCard FOREIGN KEY (idPassword) REFERENCES passwordCard(id),
	CONSTRAINT passwordCardTag_fk_passwordTag FOREIGN KEY (idTag) REFERENCES passwordTag(id)
);

CREATE TABLE toDelete (
	id INTEGER,
	type TEXT(80)
);
