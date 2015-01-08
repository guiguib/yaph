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
			daoFactory.getPasswordDAO().insert(password);
		} else { 
			daoFactory.getPasswordDAO().updateByPrimaryKey(password);
		}
		
		PasswordFieldExample example = new PasswordFieldExample();
		example.createCriteria().andIdCardEqualTo(password.getId());
		daoFactory.getPasswordFieldDAO().deleteByExample(example);
		
		for (PasswordField field : fields) {
			field.setIdCard(password.getId());
			field.setId(null);
			daoFactory.getPasswordFieldDAO().insert(field);
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

	@Override
	public void delete(PasswordCard password) throws ServiceException {
		
		//Suppression des champs
		PasswordFieldExample example = new PasswordFieldExample();
		example.createCriteria().andIdCardEqualTo(password.getId());
		daoFactory.getPasswordFieldDAO().deleteByExample(example);
		
		//Suppression du mot de passe
		daoFactory.getPasswordDAO().deleteByPrimaryKey(password.getId());
		
	}

}
