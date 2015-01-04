package com.gbourquet.yaph.service.password.out;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;

public class AllPasswordResult implements Result {
    
	private List<PasswordCard> passwordList;
    
    /** For serialization only. */
    AllPasswordResult() {
    }

    public AllPasswordResult(List<PasswordCard> passwordList) {
    	this.passwordList = passwordList;
    }

    public List<PasswordCard> getPasswordCardList() {
        return passwordList;
    }
}