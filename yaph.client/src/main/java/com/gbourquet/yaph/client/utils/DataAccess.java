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

	int maxIdPasswd = 0;
	int maxIdField = 0;

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
		getMaxIdPasswd();
		getMaxIdField();
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

	public List<PasswordCard> getPasswords(Account account) {
		JavaScriptObject sqlResults = sqlDb
				.execute("select id, titre, user, password, adresse, account from passwordCard where account = "
						+ account.getId());

		return deserializeRecords(sqlResults, "passwordCard");
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

	public int insertPassword(Account account, PasswordCard password) {
		maxIdPasswd--;
		StringBuffer sb = new StringBuffer(
				"insert into passwordCard (id, titre, user, password, adresse, account) values(");
		sb.append(maxIdPasswd).append(",");
		sb.append("'").append(password.getTitre()).append("',");
		sb.append("'").append(password.getUser()).append("',");
		sb.append("'").append(password.getPassword()).append("',");
		sb.append("'").append(password.getAdresse()).append("',");
		sb.append(account.getId()).append(")");
		JavaScriptObject jso = sqlDb.execute(sb.toString());
		GWT.log(jso.toString());
		persistDB();

		return maxIdPasswd;
	}

	public void insertPasswordFields(PasswordCard password,
			List<PasswordField> fields) {
		for (PasswordField field : fields) {
			maxIdField--;
			StringBuffer sb = new StringBuffer(
					"insert into passwordField (id, idCard, type, libelle, value) values(");
			sb.append(maxIdField).append(",");
			sb.append(password.getId()).append(",");
			sb.append("'").append(field.getType()).append("',");
			sb.append("'").append(field.getLibelle()).append("',");
			sb.append("'").append(field.getValue()).append("')");

			JavaScriptObject jso = sqlDb.execute(sb.toString());
			GWT.log(jso.toString());
			persistDB();
		}

	}

	public int getMaxIdPasswd() {
		if (maxIdPasswd == 0) {
			JavaScriptObject sqlResults = sqlDb.execute("select min(id) id "
					+ "from passwordCard");

			SQLiteResult rows = new SQLiteResult(sqlResults);

			if (rows.size() != 1)
				for (SQLiteResult.Row row : rows) {
					if (row != null)
						maxIdPasswd = row.getInt("id");
				}
		}

		return maxIdPasswd;
	}

	public int getMaxIdField() {
		if (maxIdField == 0) {
			JavaScriptObject sqlResults = sqlDb.execute("select min(id) id "
					+ "from passwordField");

			SQLiteResult rows = new SQLiteResult(sqlResults);
			if (rows.size() != 1)
				for (SQLiteResult.Row row : rows)
					maxIdField = row.getInt("id");
		}
		return maxIdField;
	}

}
