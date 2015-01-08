package com.gbourquet.yaph.client.serialization;

import java.util.ArrayList;

import com.gbourquet.yaph.client.utils.SQLiteResult.Row;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;

public class PasswordFieldSerializer extends BaseSerializer<PasswordField> {
	public PasswordFieldSerializer() {
		super("passwordField");

		fields = new ArrayList<FieldSerializer<PasswordField>>();

		fields.add(new IntFieldSerializer<PasswordField>("id") {
			@Override
			protected Integer getValue(PasswordField dto) {
				return dto.getId();
			}
		});

		fields.add(new IntFieldSerializer<PasswordField>("idCard") {
			@Override
			protected Integer getValue(PasswordField dto) {
				return dto.getIdCard();
			}
		});

		fields.add(new StringFieldSerializer<PasswordField>("type") {
			@Override
			protected String getValue(PasswordField dto) {
				return dto.getType();
			}
		});

		fields.add(new StringFieldSerializer<PasswordField>("libelle") {
			@Override
			protected String getValue(PasswordField dto) {
				return dto.getLibelle();
			}
		});

		fields.add(new StringFieldSerializer<PasswordField>("value") {
			@Override
			protected String getValue(PasswordField dto) {
				return dto.getValue();
			}
		});
	}

	@Override
	public PasswordField rowToDto(Row row) {
		PasswordField field = new PasswordField();
		field.setId(row.getInt("id"));
		field.setIdCard(row.getInt("idCard"));
		field.setLibelle(row.getString("libelle"));
		field.setType(row.getString("type"));
		field.setValue(row.getString("value"));

		return field;

	}

}
