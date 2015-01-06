package com.gbourquet.yaph.serveur.service;

import java.util.List;

import com.gbourquet.yaph.dao.DaoFactory;
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
	public PasswordCard save(PasswordCard password,List<PasswordField> fields) throws ServiceException {
		if (password.getId()==null || password.getId()==0)
		{
			//c'est un nouveau mot de passe à créer
			int passwordId = daoFactory.getPasswordDAO().insert(password);
			password.setId(passwordId);
			
		} else { 
			daoFactory.getPasswordDAO().updateByPrimaryKey(password);
		}
		
		for (PasswordField field : fields) {
			field.setIdCard(password.getId());
			if (field.getId()==null || field.getId()==0)
			{
				//c'est un nouveau mot de passe à créer
				daoFactory.getPasswordFieldDAO().insert(field);
			} else { 
				daoFactory.getPasswordFieldDAO().updateByPrimaryKey(field);
			}
		}
		
		return password;
	}

	@Override
	public List<PasswordCard> getPasswords(Account account)	throws ServiceException {
		
		PasswordCardExample passwordExample = new PasswordCardExample();
		com.gbourquet.yaph.serveur.metier.generated.PasswordCardExample.Criteria critere = passwordExample.createCriteria();
		critere.andAccountEqualTo(account.getId());
		
		return daoFactory.getPasswordDAO().selectByExample(passwordExample);

	}

	@Override
	public List<PasswordField> getFields(PasswordCard password)	throws ServiceException {
		PasswordFieldExample passwordField = new PasswordFieldExample();
		com.gbourquet.yaph.serveur.metier.generated.PasswordFieldExample.Criteria critere = passwordField.createCriteria();
		critere.andIdCardEqualTo(password.getId());
		
		return daoFactory.getPasswordFieldDAO().selectByExample(passwordField);
	}

}
