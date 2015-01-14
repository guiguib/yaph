package com.gbourquet.yaph.serveur.password.in;

import java.util.HashMap;
import java.util.List;

import com.gbourquet.yaph.serveur.in.AbstractAction;
import com.gbourquet.yaph.serveur.metier.generated.Account;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.gbourquet.yaph.serveur.password.out.SyncPasswordResult;

public class SyncPasswordAction extends AbstractAction<SyncPasswordResult> {

	private Account account;
	private List<PasswordCard> passwordsToDelete;
	private HashMap<PasswordCard,List<PasswordField>> dataToUpdate;
	
	/** For serialization only. */
	SyncPasswordAction() {
	}

	public SyncPasswordAction(Account account, List<PasswordCard> passwordsToDelete, HashMap<PasswordCard, List<PasswordField>> dataToUpdate) {
		super();
		this.account = account;
		this.passwordsToDelete = passwordsToDelete;
		this.dataToUpdate = dataToUpdate;
	}

	public Account getAccount() {
		return account;
	}

	public List<PasswordCard> getPasswordsToDelete() {
		return passwordsToDelete;
	}

	public HashMap<PasswordCard,List<PasswordField>> getDataToUpdate() {
		return dataToUpdate;
	}
}