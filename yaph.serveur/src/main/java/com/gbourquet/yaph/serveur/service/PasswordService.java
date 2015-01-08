package com.gbourquet.yaph.serveur.service;

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

    /**
     * service pour enregistrer un mot de passe.
     * @param password
     *            mot de passe à enregistrer
     * @return password
     * @throws ServiceException 
     */
    List<PasswordCard> getPasswords(final Account account) throws ServiceException;
	PasswordCard save(PasswordCard password, List<PasswordField> fields) throws ServiceException;
	List<PasswordField> getFields(final PasswordCard password) throws ServiceException;
	void delete(PasswordCard password) throws ServiceException;
	
}
