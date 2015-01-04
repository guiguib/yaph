package com.gbourquet.yaph.service.password.out;

import net.customware.gwt.dispatch.shared.Result;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;

public class PasswordResult implements Result {
    
	private PasswordCard password;
	
    /** For serialization only. */
    PasswordResult() {
    }

    public PasswordResult(PasswordCard password) {
        this.password = password;
    }

    public PasswordCard getPasswordCard() {
        return password;
    }
        
}