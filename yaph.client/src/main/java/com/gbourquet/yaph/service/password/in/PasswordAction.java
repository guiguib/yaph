package com.gbourquet.yaph.service.password.in;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.service.in.AbstractAction;
import com.gbourquet.yaph.service.password.out.PasswordResult;

public class PasswordAction extends AbstractAction<PasswordResult> {
    
	private PasswordCard password;
	
    /** For serialization only. */
    PasswordAction() {
    }
 
    public PasswordAction(PasswordCard password) {
        super();
        this.password = password;
    }

    public PasswordCard getPasswordCard() {
        return this.password;
    }
       
}