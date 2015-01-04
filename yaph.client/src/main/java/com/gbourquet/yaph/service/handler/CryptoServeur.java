package com.gbourquet.yaph.service.handler;

import com.googlecode.gwt.crypto.bouncycastle.DataLengthException;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

/**
 * This class provides methods to encrypt and decrypt data.
 */
public class CryptoServeur {

    public String encrypt(final String dataToEncrypt, final String secretKey) {
    	
    	String encryptedData=null;
    	
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
}