package com.gbourquet.yaph.serveur.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.gbourquet.yaph.dao.DaoFactory;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.AccountExample;
import com.gbourquet.yaph.serveur.metier.generated.AccountExample.Criteria;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;

/**
 * Classe de service pour se logguer à l'application.
 * 
 * @author guillaume
 */
public class LoginServiceImpl implements LoginService {

	DaoFactory daoFactory = null;

	/**
	 * Methode exécutant le service.
	 * 
	 * @param email
	 *            email de log
	 * @param password
	 *            mot de passe
	 * @return L'utilisateur qui s'est loggué
	 * @throws ServiceException
	 */
	@Override
	public final Account login(final String ident, final String password)
			throws ServiceException {

		AccountExample requeteAccount = new AccountExample();
		Criteria critere = requeteAccount
				.createCriteria();
		critere.andLoginEqualTo(ident);
		//critere.andPasswordEqualTo(encrypt(password));
		critere.andPasswordEqualTo(password);
		

		List<Account> util = daoFactory.getPersonneDAO().selectByExample(
				requeteAccount);

		if (util == null || util.size() == 0) {
			throw new ServiceException("Compte inconnu");
		}

		Date aujourdui = new Date();
		boolean actif = false;
		for (Account account : util) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date accountActivationDate=null;
				Date accountDesactivationDate=null;
				if (account.getDateActivation() != null)
					accountActivationDate = df.parse(account
						.getDateActivation());
				if (account.getDateDesactivation() != null)
					accountDesactivationDate = df.parse(account
						.getDateDesactivation());

				actif = aujourdui.after(accountActivationDate)
						&& (accountDesactivationDate == null || aujourdui
								.before(accountDesactivationDate));
			} catch (ParseException e) {
				throw new ServiceException("Date incorrecte");
			}
			if (actif)
				return account;
		}

		throw new ServiceException("Account desactivated");
	}

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@SuppressWarnings("unused")
	private final String encrypt(String plaintext) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA"); // step 2
		} catch (NoSuchAlgorithmException e) {
			return "cryptage impossible";
		}
		try {
			md.update(plaintext.getBytes("UTF-8")); // step 3
		} catch (UnsupportedEncodingException e) {
			return "cryptage impossible";
		}
		byte raw[] = md.digest(); // step 4
		byte[] hash = (new Base64()).encode(raw); // step 5
		return new String(hash); // step 6
	}

}
