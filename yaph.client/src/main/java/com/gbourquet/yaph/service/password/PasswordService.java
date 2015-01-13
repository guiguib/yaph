package com.gbourquet.yaph.service.password;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public interface PasswordService {

	public void insertPassword(PasswordCard password, List<PasswordField> fields);
	public void updatePassword(PasswordCard password, List<PasswordField> fields);
	public void deletePassword(PasswordCard password);
}
