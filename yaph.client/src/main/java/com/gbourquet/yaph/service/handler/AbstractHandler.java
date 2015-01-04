package com.gbourquet.yaph.service.handler;

import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gbourquet.yaph.service.in.AbstractAction;
import com.gbourquet.yaph.service.login.exceptions.IllegalUserException;


//abstract class AbstractGeneric1<T extends MyObject> implements IGeneric<T> { }

public abstract class AbstractHandler<A extends AbstractAction<R>, R extends Result> implements ActionHandler<A, R>{
	
	final static protected HttpSession session() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true); // true == allow create
	}
	
	public final R execute(A action, ExecutionContext context) throws ActionException {
		//TODO : Gestion des droits
		//HttpSession session = session();
		//String token = (String) session.getAttribute("token");
		
		/*if (token != null && !token.equals(action.getToken()))
			throw new IllegalUserException();
		*/
		boolean present=true; //à modifier - tout le monde a tous les droits pour le moment
		
		if (!present)
        	throw new IllegalUserException();
		
		return exec(action, context);
	}
		
	public abstract R exec(A action, ExecutionContext context) throws ActionException;

}
