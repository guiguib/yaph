package com.gbourquet.yaph.client.serialization;

import com.gbourquet.yaph.client.utils.Record;

public abstract class StringFieldSerializer<T> implements FieldSerializer<T> {
	abstract String getValue(T dto);

	private String name;

	public StringFieldSerializer(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSqlQuoted(T dto) {
		String value = getValue(dto);
		return "'" + value + "'";
	}

	@Override
	public String getSqlQuoted(Record info) {
		return "'" + info.getString(name) + "'";
	}
}