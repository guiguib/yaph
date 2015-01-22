package com.gbourquet.yaph.serveur.service;

import java.util.HashMap;
import java.util.List;

import com.gbourquet.yaph.dao.DaoFactory;
import com.gbourquet.yaph.dao.generated.PasswordCardMapper;
import com.gbourquet.yaph.dao.generated.PasswordFieldMapper;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCardExample;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.metier.generated.PasswordFieldExample;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;

/**
 * Classe de service pour enregistrer un mot de passe.
 * 
 * @author guillaume
 */
public class PasswordServiceImpl implements PasswordService {

	DaoFactory daoFactory = null;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public PasswordCard save(PasswordCard password, List<PasswordField> fields, Account account) throws ServiceException {
		PasswordCardMapper passwordDao = daoFactory.getPasswordDAO();
		PasswordFieldMapper fieldDao = daoFactory.getPasswordFieldDAO();

		if (password.getId() == null || password.getId() <= 0) {
			// c'est un nouveau mot de passe à créer
			password.setId(null);
			passwordDao.insert(password);
		} else {
			
			// Securité : le mot de passe et les champs, doivent appartenir au
			// compte en session
			PasswordCardExample requete = new PasswordCardExample();
			requete.createCriteria().andAccountEqualTo(account.getId()).andIdEqualTo(password.getId());
			int nbPassword = passwordDao.countByExample(requete);
			if (nbPassword != 1)
				throw new ServiceException("Mise à jour de mot de passe d'un autre compte. idAccount="+account.getId()+" idAccount dans Passwd="+password.getAccount()+" idPasswd="+password.getId());

			passwordDao.updateByPrimaryKey(password);
		}

		PasswordFieldExample example = new PasswordFieldExample();
		example.createCriteria().andIdCardEqualTo(password.getId());
		fieldDao.deleteByExample(example);

		for (PasswordField field : fields) {
			field.setIdCard(password.getId());
			field.setId(null);
			fieldDao.insert(field);
		}

		return password;
	}

	@Override
	public HashMap<PasswordCard, List<PasswordField>> getPasswords(Account account) throws ServiceException {

		PasswordCardExample passwordExample = new PasswordCardExample();
		com.gbourquet.yaph.serveur.metier.generated.PasswordCardExample.Criteria critere = passwordExample.createCriteria();
		critere.andAccountEqualTo(account.getId());

		HashMap<PasswordCard, List<PasswordField>> retour = new HashMap<PasswordCard, List<PasswordField>>();
		List<PasswordCard> passwords = daoFactory.getPasswordDAO().selectByExample(passwordExample);
		for (PasswordCard lPassword : passwords)
			retour.put(lPassword, getFields(lPassword, account));

		return retour;

	}

	@Override
	public List<PasswordField> getFields(PasswordCard password, Account account) throws ServiceException {
		PasswordFieldExample passwordField = new PasswordFieldExample();
		com.gbourquet.yaph.serveur.metier.generated.PasswordFieldExample.Criteria critere = passwordField.createCriteria();
		critere.andIdCardEqualTo(password.getId());
		// Securité : le mot de passe et les champs, doivent appartenir au
		// compte en session
		PasswordCardExample requete = new PasswordCardExample();
		requete.createCriteria().andAccountEqualTo(account.getId()).andIdEqualTo(password.getId());
		int nbPassword = daoFactory.getPasswordDAO().countByExample(requete);
		if (nbPassword != 1)
			throw new ServiceException("Recherche de mot de passe d'un autre compte");

		return daoFactory.getPasswordFieldDAO().selectByExample(passwordField);
	}

	@Override
	public void delete(PasswordCard password, Account account) throws ServiceException {

		// Securité : le mot de passe et les champs, doivent appartenir au compte en session
		PasswordCardExample requete = new PasswordCardExample();
		requete.createCriteria().andAccountEqualTo(account.getId()).andIdEqualTo(password.getId());
		int nbPassword = daoFactory.getPasswordDAO().countByExample(requete);
		if (nbPassword != 1)
			throw new ServiceException("Suppression de mot de passe d'un autre compte");
		
		// Suppression des champs
		PasswordFieldExample example = new PasswordFieldExample();
		example.createCriteria().andIdCardEqualTo(password.getId());
		daoFactory.getPasswordFieldDAO().deleteByExample(example);

		// Suppression du mot de passe
		daoFactory.getPasswordDAO().deleteByPrimaryKey(password.getId());

	}

	@Override
	public HashMap<PasswordCard, List<PasswordField>> sync(Account account, List<PasswordCard> passwordToDelete, HashMap<PasswordCard, List<PasswordField>> dataToUpdate)
			throws ServiceException {
		for (PasswordCard lPassword : passwordToDelete)
			delete(lPassword, account);
		for (PasswordCard lPassword : dataToUpdate.keySet()) {
			List<PasswordField> lFields = dataToUpdate.get(lPassword);
			save(lPassword, lFields, account);
		}

		return getPasswords(account);
	}

}
