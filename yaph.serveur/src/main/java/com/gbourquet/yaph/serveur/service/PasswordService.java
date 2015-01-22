package com.gbourquet.yaph.serveur.service;

import java.util.HashMap;
import java.util.List;

import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;

/**
 * Interface pour les services Password.
 * @author guillaume
 */
public interface PasswordService extends Service {

    HashMap<PasswordCard, List<PasswordField>> getPasswords(final Account account) throws ServiceException;
	PasswordCard save(PasswordCard password, List<PasswordField> fields,Account account) throws ServiceException;
	List<PasswordField> getFields(final PasswordCard password, Account account) throws ServiceException;
	void delete(PasswordCard password, Account account) throws ServiceException;
	HashMap<PasswordCard,List<PasswordField>> sync(Account account, List<PasswordCard> passwordToDelete, HashMap<PasswordCard, List<PasswordField>> dataToUpdate) throws ServiceException;
	
}
