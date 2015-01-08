package com.gbourquet.yaph.service.password.in;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.service.in.AbstractAction;
import com.gbourquet.yaph.service.password.out.DeletePasswordResult;

public class DeletePasswordAction extends AbstractAction<DeletePasswordResult> {
    
	private PasswordCard password;
	
    /** For serialization only. */
    DeletePasswordAction() {
    }
 
    public DeletePasswordAction(PasswordCard password) {
        super();
        this.password = password;
    }

    public PasswordCard getPasswordCard() {
        return this.password;
    }     
}