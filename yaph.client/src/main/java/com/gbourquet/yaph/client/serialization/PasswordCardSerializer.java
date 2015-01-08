package com.gbourquet.yaph.client.serialization;

import java.util.ArrayList;

import com.gbourquet.yaph.client.utils.SQLiteResult.Row;
import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;

public class PasswordCardSerializer extends BaseSerializer<PasswordCard> {
	public PasswordCardSerializer() {
		super("passwordCard");

		fields = new ArrayList<FieldSerializer<PasswordCard>>();

		fields.add(new StringFieldSerializer<PasswordCard>("titre") {
			@Override
			protected String getValue(PasswordCard dto) {
				return dto.getTitre();
			}
		});

		fields.add(new StringFieldSerializer<PasswordCard>("user") {
			@Override
			protected String getValue(PasswordCard dto) {
				return dto.getUser();
			}
		});

		fields.add(new StringFieldSerializer<PasswordCard>("password") {
			@Override
			protected String getValue(PasswordCard dto) {
				return dto.getPassword();
			}
		});

		fields.add(new StringFieldSerializer<PasswordCard>("adresse") {
			@Override
			protected String getValue(PasswordCard dto) {
				return dto.getAdresse();
			}
		});

		fields.add(new IntFieldSerializer<PasswordCard>("account") {
			@Override
			protected Integer getValue(PasswordCard dto) {
				return dto.getAccount();
			}
		});
	}

	@Override
	public PasswordCard rowToDto(Row row) {
		PasswordCard password = new PasswordCard();
		password.setId(row.getInt("id"));
		password.setTitre(row.getString("titre"));
		password.setUser(row.getString("user"));
		password.setPassword(row.getString("password"));
		password.setAdresse(row.getString("adresse"));
		password.setAccount(row.getInt("account"));

		return password;

	}

}
