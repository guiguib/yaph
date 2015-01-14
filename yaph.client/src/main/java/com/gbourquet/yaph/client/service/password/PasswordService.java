package com.gbourquet.yaph.client.service.password;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public interface PasswordService {

	public void savePassword(PasswordCard password, List<PasswordField> fields);
	public void deletePassword(PasswordCard password);
	public void getAllPassword(Account account);
	public void savePasswords(Account account, List<PasswordCard> passwords, List<PasswordField> fields);
}
