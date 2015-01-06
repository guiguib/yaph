package com.gbourquet.yaph.client.serialization;



public class Serializer
{
	private static AccountSerializer accountSerializer = new AccountSerializer();
	private static PasswordCardSerializer passwordCardSerializer = new PasswordCardSerializer();
	private static PasswordFieldSerializer passwordFieldSerializer = new PasswordFieldSerializer();
	
	@SuppressWarnings( "unchecked" )
	public static <T> TableRecordSerializer<T> getSerializer( String table )
	{
		if( table.equals( "account" ) )
			return (TableRecordSerializer<T>) accountSerializer;
		if( table.equals( "passwordCard" ) )
			return (TableRecordSerializer<T>) passwordCardSerializer;
		if( table.equals( "passwordField" ) )
			return (TableRecordSerializer<T>) passwordFieldSerializer;
		
		assert false : "No serializer for table " + table;
		return null;
	}
}
