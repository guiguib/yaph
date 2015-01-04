package com.gbourquet.yaph.serveur.metier;


/*
 * BaseDTO class for client side. Allows to track changes made in memory, to save them later on.
 */
public abstract class BaseDto
{
	// tells if a setter has been called since object's construction
	protected boolean isChanged = false;

	protected String updateDate;

	protected BaseDto()
	{
	}

	public abstract Integer getId();

	public abstract void setId( Integer id );

	public final boolean isChanged()
	{
		return isChanged;
	}

	public final void commitChange()
	{
		isChanged = false;
	}

	public final String getUpdateDate()
	{
		return updateDate;
	}

	public final void setUpdateDate( String update_date )
	{
		isChanged = true;
		this.updateDate = update_date;
	}
}
