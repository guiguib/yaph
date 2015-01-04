package com.gbourquet.yaph.serveur.service;

import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;

/**
 * Interface pour les services Login.
 * @author guillaume
 */
public interface LoginService extends Service {

    /**
     * service pour se logguer.
     * @param email
     *            email de l'utilisateur
     * @param password
     *            mot de passe de l'utilisateur
     * @return Utilisateur
     * @throws ServiceException 
     */
    Account login(final String login, final String password) throws ServiceException;
}
