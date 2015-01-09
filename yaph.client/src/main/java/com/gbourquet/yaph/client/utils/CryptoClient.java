package com.gbourquet.yaph.client.utils;

import com.gbourquet.yaph.serveur.metier.generated.PasswordCard;
import com.gbourquet.yaph.serveur.metier.generated.PasswordField;
import com.googlecode.gwt.crypto.bouncycastle.DataLengthException;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

/**
 * This class provides methods to encrypt and decrypt data.
 */
public class CryptoClient {

	public String encrypt(final String dataToEncrypt, final String secretKey) {

		String encryptedData = null;

		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(secretKey.getBytes());
		try {
			encryptedData = cipher.encrypt(String.valueOf(dataToEncrypt));
		} catch (DataLengthException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (InvalidCipherTextException e1) {
			e1.printStackTrace();
		}

		return encryptedData;
	}

	public String decrypt(final String encryptedData, final String secretKey) {

		String decryptedData = null;

		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(secretKey.getBytes());
		try {
			decryptedData = cipher.decrypt(encryptedData);
		} catch (DataLengthException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			e.printStackTrace();
		}
		return decryptedData;
	}

	public PasswordCard crypt(final PasswordCard dataToEncrypt, final String secretKey) {

		PasswordCard encryptedData = new PasswordCard();

		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(secretKey.getBytes());
		try {
			encryptedData.setAccount(dataToEncrypt.getAccount());
			encryptedData.setAdresse(dataToEncrypt.getAdresse());
			encryptedData.setId(dataToEncrypt.getId());
			encryptedData.setPassword(dataToEncrypt.getPassword());
			encryptedData.setTitre(cipher.encrypt(String.valueOf(dataToEncrypt.getTitre())));
			encryptedData.setUpdateDate(dataToEncrypt.getUpdateDate());
			encryptedData.setUser(dataToEncrypt.getUser());

		} catch (DataLengthException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (InvalidCipherTextException e1) {
			e1.printStackTrace();
		}

		return encryptedData;

	}

	public PasswordCard decrypt(final PasswordCard dataToDecrypt, final String secretKey) {

		PasswordCard decryptedData = new PasswordCard();

		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(secretKey.getBytes());
		try {
			decryptedData.setAccount(dataToDecrypt.getAccount());
			decryptedData.setAdresse(dataToDecrypt.getAdresse());
			decryptedData.setId(dataToDecrypt.getId());
			decryptedData.setPassword(dataToDecrypt.getPassword());
			decryptedData.setTitre(cipher.decrypt(dataToDecrypt.getTitre()));
			decryptedData.setUpdateDate(dataToDecrypt.getUpdateDate());
			decryptedData.setUser(dataToDecrypt.getUser());

		} catch (DataLengthException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (InvalidCipherTextException e1) {
			e1.printStackTrace();
		}

		return decryptedData;

	}

	public PasswordField crypt(final PasswordField dataToEncrypt, final String secretKey) {

		PasswordField encryptedData = new PasswordField();

		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(secretKey.getBytes());
		try {
			encryptedData.setId(dataToEncrypt.getId());
			encryptedData.setIdCard(dataToEncrypt.getIdCard());
			encryptedData.setLibelle(cipher.encrypt(String.valueOf(dataToEncrypt.getLibelle())));
			encryptedData.setType(dataToEncrypt.getType());
			encryptedData.setUpdateDate(dataToEncrypt.getUpdateDate());
			encryptedData.setValue(cipher.encrypt(String.valueOf(dataToEncrypt.getValue())));
		} catch (DataLengthException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (InvalidCipherTextException e1) {
			e1.printStackTrace();
		}

		return encryptedData;

	}

	public PasswordField decrypt(final PasswordField dataToDecrypt, final String secretKey) {

		PasswordField decryptedData = new PasswordField();

		TripleDesCipher cipher = new TripleDesCipher();
		cipher.setKey(secretKey.getBytes());
		try {
			decryptedData.setId(dataToDecrypt.getId());
			decryptedData.setIdCard(dataToDecrypt.getIdCard());
			decryptedData.setLibelle(cipher.decrypt(dataToDecrypt.getLibelle()));
			decryptedData.setType(dataToDecrypt.getType());
			decryptedData.setUpdateDate(dataToDecrypt.getUpdateDate());
			decryptedData.setValue(cipher.decrypt(dataToDecrypt.getValue()));
		} catch (DataLengthException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (InvalidCipherTextException e1) {
			e1.printStackTrace();
		}

		return decryptedData;

	}

}