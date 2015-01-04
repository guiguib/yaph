package com.gbourquet.yaph.client.serialization;

import java.util.ArrayList;

import com.gbourquet.yaph.client.utils.SQLiteResult.Row;
import com.gbourquet.yaph.serveur.metier.generated.Account;

public class AccountSerializer extends BaseSerializer<Account>
{
	public AccountSerializer()
	{
		super( "account" );

		fields = new ArrayList<FieldSerializer<Account>>();

		fields.add( new StringFieldSerializer<Account>( "login" )
				{
					@Override
					protected String getValue( Account dto )
					{
						return dto.getLogin();
					}
				} );

		fields.add( new StringFieldSerializer<Account>( "password" )
				{
					@Override
					protected String getValue( Account dto )
					{
						return dto.getPassword();
					}
				} );

		fields.add( new StringFieldSerializer<Account>( "nom" )
				{
					@Override
					protected String getValue( Account dto )
					{
						return dto.getNom();
					}
				} );



		fields.add( new StringFieldSerializer<Account>( "prenom" )
				{
					@Override
					protected String getValue( Account dto )
					{
						return dto.getPrenom();
					}
				} );

		fields.add( new StringFieldSerializer<Account>( "dateActivation" )
				{
					@Override
					protected String getValue( Account dto )
					{
						return dto.getDateActivation();
					}
				} );
		
		fields.add( new StringFieldSerializer<Account>( "dateDesactivation" )
				{
					@Override
					protected String getValue( Account dto )
					{
						return dto.getDateDesactivation();
					}
				} );
	}

	@Override
	public Account rowToDto( Row row )
	{
		Account account = new Account();
		account.setId(row.getInt("id"));
		account.setLogin(row.getString("login"));
		account.setPassword(row.getString("password"));
		account.setNom(row.getString("nom"));
		account.setPrenom(row.getString("prenom"));
		account.setDateActivation(row.getString("dateActivation"));
		account.setDateDesactivation(row.getString("dateDesactivation"));
		
		return account;
	}

}
