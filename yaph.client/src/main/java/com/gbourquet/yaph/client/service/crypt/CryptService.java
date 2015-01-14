package com.gbourquet.yaph.client.service.crypt;

import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public interface CryptService {
	
	public List<PasswordField> crypt(List<PasswordField> fieldsData);
	public PasswordCard crypt(PasswordCard password);
	public PasswordCard decrypt(PasswordCard password);
	public List<PasswordField> decrypt(List<PasswordField> fieldsData);
	public String decrypt(String titre);
	
}
