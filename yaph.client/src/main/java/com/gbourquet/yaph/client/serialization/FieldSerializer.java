package com.gbourquet.yaph.client.serialization;

import com.gbourquet.yaph.client.utils.Record;


public interface FieldSerializer<T>
{
	// get the field name
	String getName();

	// get the quoted sql value needed from a dto object
	String getSqlQuoted( T dto );

	// get the quoted sql value needed from a record (which contains just a hashmap of all the field values)
	String getSqlQuoted( Record info );
}