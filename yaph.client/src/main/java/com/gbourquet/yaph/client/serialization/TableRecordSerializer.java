package com.gbourquet.yaph.client.serialization;

import com.gbourquet.yaph.client.utils.SQLiteResult.Row;

public interface TableRecordSerializer<T> {
	// constructs a DTO object from a SQLite result row
	T rowToDto(Row row);

	// constructs an insert statement corresponding to the DTO data
	String dtoToSqlInsert(int id, T dto);

	// constructs an update statement corresponding to the DTO data
	String dtoToSqlUpdate(T dto);
}
