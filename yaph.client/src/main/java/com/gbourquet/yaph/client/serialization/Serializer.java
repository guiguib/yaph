package com.gbourquet.yaph.client.serialization;



public class Serializer
{
	private static AccountSerializer accountSerializer = new AccountSerializer();
	private static PasswordCardSerializer passwordCardSerializer = new PasswordCardSerializer();
	
	@SuppressWarnings( "unchecked" )
	public static <T> TableRecordSerializer<T> getSerializer( String table )
	{
		if( table.equals( "account" ) )
			return (TableRecordSerializer<T>) accountSerializer;
		if( table.equals( "passwordCard" ) )
			return (TableRecordSerializer<T>) passwordCardSerializer;
		
		assert false : "No serializer for table " + table;
		return null;
	}
}
