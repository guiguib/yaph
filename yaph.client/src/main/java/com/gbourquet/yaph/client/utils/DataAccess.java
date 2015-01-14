package com.gbourquet.yaph.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.gbourquet.yaph.client.serialization.Serializer;
import com.gbourquet.yaph.client.serialization.TableRecordSerializer;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.storage.client.Storage;

public class DataAccess {

	int minIdPasswd = 0;
	int minIdField = 0;

	private static final String LOCALSTORAGE_KEY_DB = "db";

	// ClientBundle containing the SQL file which is used to initialize the
	// SQLite database
	interface DbSchema extends ClientBundle {
		@Source("schema.sql")
		TextResource sqlForSchema();
	}

	private final Storage storage = Storage.getLocalStorageIfSupported();
	private SQLite sqlDb;

	private static DataAccess instance;

	public static DataAccess getInstance() {
		if (instance == null)
			instance = new DataAccess();

		return instance;
	}

	private DataAccess() {
	}

	/*
	 * Initialize the local database, ie : loads it or create it if not found
	 */
	public void init() {
		// try to get the serialized representation of the SQLite DB from the
		// local storage
		String serializedDb = storage.getItem(LOCALSTORAGE_KEY_DB);

		if (serializedDb == null || serializedDb.isEmpty()) {
			// if nothing is found, we create the database from scratch
			sqlDb = SQLite.create();

			// and inject the SQL file which creates the tables structure
			DbSchema dbSchema = (DbSchema) GWT.create(DbSchema.class);
			sqlDb.execute(dbSchema.sqlForSchema().getText());
		} else {
			// if the local storage already contains some data, parse it as a
			// JSON integer array
			JSONValue dbContent = JSONParser.parseStrict(serializedDb);

			// and initialize SQLite with this "file"
			sqlDb = SQLite.create(dbContent.isArray().getJavaScriptObject()
					.<JsArrayInteger> cast());
		}

		persistDB();
		getMinIdPasswd();
		getMinIdField();
	}

	private void persistDB() {
		JSONArray dbData = new JSONArray(sqlDb.exportData());
		storage.setItem(LOCALSTORAGE_KEY_DB, dbData.toString());
	}

	/*
	 * Exports the SQLite database file as an array of integer
	 */
	public JsArrayInteger exportDbData() {
		return sqlDb.exportData();
	}

	public void cleanLocalStorage() {
		storage.clear();
	}

	public SQLite getSqlDb() {
		return sqlDb;
	}

	public int getMinIdPasswd() {
		if (minIdPasswd == 0) {
			JavaScriptObject sqlResults = sqlDb.execute("select min(id) id "
					+ "from passwordCard");

			SQLiteResult rows = new SQLiteResult(sqlResults);

			if (rows.size() != 1)
				for (SQLiteResult.Row row : rows) {
					if (row != null)
						minIdPasswd = row.getInt("id");
				}
		}

		return minIdPasswd;
	}

	public int getMinIdField() {
		if (minIdField == 0) {
			JavaScriptObject sqlResults = sqlDb.execute("select min(id) id "
					+ "from passwordField");

			SQLiteResult rows = new SQLiteResult(sqlResults);
			if (rows.size() != 1)
				for (SQLiteResult.Row row : rows)
					minIdField = row.getInt("id");
		}
		return minIdField;
	}

	public List<PasswordCard> getPasswords(Account account) {
		JavaScriptObject sqlResults = sqlDb
				.execute("select id, titre, user, password, adresse, account from passwordCard where account = "
						+ account.getId()+" order by titre asc");

		return deserializeRecords(sqlResults, "passwordCard");
	}

	public List<PasswordField> getFields(PasswordCard password) {
		JavaScriptObject sqlResults = sqlDb
				.execute("select id, idCard, type, libelle, value from passwordField where idCard = "
						+ password.getId());

		return deserializeRecords(sqlResults, "passwordField");
	}

	public Account getAccount() {
		JavaScriptObject sqlResults = sqlDb
				.execute("select id, nom, prenom, login, password, dateActivation, dateDesactivation from account");

		List<Account> accounts = deserializeRecords(sqlResults, "account");
		if (accounts != null && accounts.size() > 0)
			return accounts.get(0);
		else
			return null;
	}

	public Account getAccount(String login, String passwd) {
		JavaScriptObject sqlResults = sqlDb
				.execute("select id, nom, prenom, login, password, dateActivation, dateDesactivation "
						+ "from account "
						+ "where login = '"
						+ login
						+ "' "
						+ "and password = '" + passwd + "'");

		List<Account> accounts = deserializeRecords(sqlResults, "account");
		if (accounts != null && accounts.size() > 0)
			return accounts.get(0);
		else
			return null;
	}

	public void setAccount(Account account) {
		sqlDb.execute("delete from account");
		StringBuffer sb = new StringBuffer(
				"insert into account ( id, nom, prenom, login, password, dateActivation, dateDesactivation) values(");
		sb.append("'").append(account.getId()).append("',");
		sb.append("'").append(account.getNom()).append("',");
		sb.append("'").append(account.getPrenom()).append("',");
		sb.append("'").append(account.getLogin()).append("',");
		sb.append("'").append(account.getPassword()).append("',");
		sb.append("'").append(account.getDateActivation()).append("',");
		sb.append("'")
				.append(account.getDateDesactivation() == null ? "" : account
						.getDateDesactivation()).append("')");
		sqlDb.execute(sb.toString());

		persistDB();
	}

	public void setPasswords(List<PasswordCard> passwords, Account account) {
		sqlDb.execute("delete from passwordCard");
		for (PasswordCard password : passwords) {
			StringBuffer sb = new StringBuffer(
					"insert into passwordCard ( id, titre, user, password, adresse, account) values(");
			sb.append("'").append(password.getId()).append("',");
			sb.append("'").append(password.getTitre()).append("',");
			sb.append("'").append(password.getUser()).append("',");
			sb.append("'").append(password.getPassword()).append("',");
			sb.append("'").append(password.getAdresse()).append("',");
			sb.append(account.getId()).append(")");
			sqlDb.execute(sb.toString());
		}
		
		persistDB();
	}

	public void setFields(List<PasswordField> fields) {
		sqlDb.execute("delete from passwordField");
		for (PasswordField field : fields) {
			StringBuffer sb = new StringBuffer(
					"insert into passwordField ( id, idCard, type, libelle, value) values(");
			sb.append(field.getId()).append(",");
			sb.append(field.getIdCard()).append(",");
			sb.append("'").append(field.getType()).append("',");
			sb.append("'").append(field.getLibelle()).append("',");
			sb.append("'").append(field.getValue()).append("')");
			sqlDb.execute(sb.toString());
		}
		persistDB();
	}

	private <T> List<T> deserializeRecords(JavaScriptObject sqlResults,
			String tableName) {
		TableRecordSerializer<T> recordSerializer = Serializer
				.getSerializer(tableName);

		List<T> res = new ArrayList<T>();

		SQLiteResult rows = new SQLiteResult(sqlResults);
		for (SQLiteResult.Row row : rows)
			res.add(recordSerializer.rowToDto(row));

		return res;
	}

	public int savePassword(PasswordCard password) {

		int id = password.getId() == null || password.getId() == 0 ? --minIdPasswd
				: password.getId();
		StringBuffer sb = new StringBuffer(
				"insert into passwordCard (id, titre, user, password, adresse, account) values(");
		sb.append(id).append(",");
		sb.append("'").append(password.getTitre()).append("',");
		sb.append("'").append(password.getUser()).append("',");
		sb.append("'").append(password.getPassword()).append("',");
		sb.append("'").append(password.getAdresse()).append("',");
		sb.append(password.getAccount()).append(")");
		try {
			sqlDb.execute(sb.toString());
		} catch (RuntimeException exception) {
			sb = new StringBuffer("update passwordCard set ");
			sb.append("titre='").append(password.getTitre()).append("' ");
			sb.append("where id=").append(password.getId());
			sqlDb.execute(sb.toString());
		}

		GWT.log(sb.toString());
		persistDB();

		return id;
	}

	public void savePasswordFields(PasswordCard password, List<PasswordField> fields) {
		
		StringBuffer sb = new StringBuffer("delete from passwordField where idCard=").append(password.getId());
		sqlDb.execute(sb.toString());
		
		GWT.log(sb.toString());
		
		for (PasswordField field : fields) {
			int id = field.getId() == null || field.getId() == 0 ? --minIdField
					: field.getId();
			sb=new StringBuffer("insert into passwordField (id, idCard, type, libelle, value) values(");
			sb.append(id).append(",");
			sb.append(password.getId()).append(",");
			sb.append("'").append(field.getType()).append("',");
			sb.append("'").append(field.getLibelle()).append("',");
			sb.append("'").append(field.getValue()).append("')");
			sqlDb.execute(sb.toString());
			
			GWT.log(sb.toString());
		}
		persistDB();
		
	}

	public void deleteOnlinePasswordCard(PasswordCard password) {
		StringBuffer sb = new StringBuffer(
				"delete from passwordField where idCard=");
		sb.append(password.getId());
		JavaScriptObject jso = sqlDb.execute(sb.toString());
		GWT.log(jso.toString());

		sb = new StringBuffer("delete from passwordCard where id=");
		sb.append(password.getId());
		jso = sqlDb.execute(sb.toString());
		GWT.log(jso.toString());

		sb = new StringBuffer("delete from toDelete where id=");
		sb.append(password.getId());
		sb.append(" and type='password'");
		jso = sqlDb.execute(sb.toString());
		GWT.log(jso.toString());

		persistDB();
	}

	public List<PasswordCard> getNewPasswd(Account account) {
		JavaScriptObject sqlResults = sqlDb
				.execute("select passwordCard.* from passwordCard "
						+ "left join passwordField on passwordCard.id = passwordField.idCard "
						+ "where account="+account.getId()+" and (passwordCard.id<0 or passwordField.id<0)");

		return deserializeRecords(sqlResults, "passwordCard");
	
	}
	
	public List<PasswordCard> getDelPasswd() {
		JavaScriptObject sqlResults = sqlDb
				.execute("select id, 'titre', 'user', 'password', 'adresse', 0 from toDelete "
						+ "where type='password'");

		return deserializeRecords(sqlResults, "passwordCard");
	
	}
	
	public List<PasswordField> getDelField() {
		JavaScriptObject sqlResults = sqlDb
				.execute("select id, 0, 'type', 'libelle', 'value' from toDelete "
						+ "where type='field'");

		return deserializeRecords(sqlResults, "passwordField");
	
	}
	
	public void deleteOfflinePasswordCard(PasswordCard password) {
		sqlDb.execute("insert into toDelete(id,type) values ("+password.getId()+",'password')");
		persistDB();
	}

}
