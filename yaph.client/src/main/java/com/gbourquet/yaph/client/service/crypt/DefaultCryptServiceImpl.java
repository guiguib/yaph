package com.gbourquet.yaph.client.service.crypt;

import java.util.ArrayList;
import java.util.List;

import com.gbourquet.yaph.client.LocalSession;
import com.gbourquet.yaph.client.utils.CryptoClient;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class DefaultCryptServiceImpl implements CryptService {

	@Override
	public PasswordCard crypt(PasswordCard password) {
		CryptoClient crypt = new CryptoClient();
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		String cryptKey = account.getPassword();
		// String key = crypt.decrypt(cryptKey, "MacleLogiciel");
		String key = cryptKey;
		int ln = Math.min(key.length(), 23);
		key = key.substring(0, ln);
		StringBuffer sb = new StringBuffer(key);
		for (int i = ln; i < 23; i++)
			sb.append("0");
		key = sb.toString();
		PasswordCard local = crypt.crypt(password, key);

		return local;

	}

	@Override
	public List<PasswordField> crypt(List<PasswordField> fieldsData) {
		CryptoClient crypt = new CryptoClient();
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		String cryptKey = account.getPassword();
		// String key = crypt.decrypt(cryptKey, "MacleLogiciel");
		String key = cryptKey;
		int ln = Math.min(key.length(), 23);
		key = key.substring(0, ln);
		StringBuffer sb = new StringBuffer(key);
		for (int i = ln; i < 23; i++)
			sb.append("0");
		key = sb.toString();
		List<PasswordField> ret = new ArrayList<PasswordField>();
		for (PasswordField field : fieldsData) {
			PasswordField local = crypt.crypt(field, key);
			ret.add(local);
		}

		return ret;
	}

	@Override
	public PasswordCard decrypt(PasswordCard password) {
		CryptoClient crypt = new CryptoClient();
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		String cryptKey = account.getPassword();
		// String key = crypt.decrypt(cryptKey, "MacleLogiciel");
		String key = cryptKey;
		int ln = Math.min(key.length(), 23);
		key = key.substring(0, ln);
		StringBuffer sb = new StringBuffer(key);
		for (int i = ln; i < 23; i++)
			sb.append("0");
		key = sb.toString();
		PasswordCard local = crypt.decrypt(password, key);

		return local;

	}

	@Override
	public List<PasswordField> decrypt(List<PasswordField> fieldsData) {
		CryptoClient crypt = new CryptoClient();
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		String cryptKey = account.getPassword();
		// String key = crypt.decrypt(cryptKey, "MacleLogiciel");
		String key = cryptKey;
		int ln = Math.min(key.length(), 23);
		key = key.substring(0, ln);
		StringBuffer sb = new StringBuffer(key);
		for (int i = ln; i < 23; i++)
			sb.append("0");
		key = sb.toString();
		List<PasswordField> ret = new ArrayList<PasswordField>();
		for (PasswordField field : fieldsData) {
			PasswordField local = crypt.decrypt(field, key);
			ret.add(local);
		}

		return ret;
	}

	@Override
	public String decrypt(String titre) {
		CryptoClient crypt = new CryptoClient();
		Account account = (Account) LocalSession.getInstance().getAttribute("account");
		String cryptKey = account.getPassword();
		// String key = crypt.decrypt(cryptKey, "MacleLogiciel");
		String key = cryptKey;
		int ln = Math.min(key.length(), 23);
		key = key.substring(0, ln);
		StringBuffer sb = new StringBuffer(key);
		for (int i = ln; i < 23; i++)
			sb.append("0");
		key = sb.toString();

		return crypt.decrypt(titre, key);

	}
}
