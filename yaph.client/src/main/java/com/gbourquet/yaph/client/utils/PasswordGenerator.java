package com.gbourquet.yaph.client.utils;

public class PasswordGenerator {

	private int nbChar;
	private boolean haveSpecialChar = true;
	private final String specialChars = ",?;.:ù_-#~&";
	private final String letterChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String numberChars = "0123456789";

	public PasswordGenerator(int nbChar, boolean haveSpecialChar) {
		super();
		this.nbChar = nbChar;
		this.haveSpecialChar = haveSpecialChar;
	}

	public int getNbChar() {
		return nbChar;
	}

	public void setNbChar(int nbChar) {
		this.nbChar = nbChar;
	}

	public boolean isHaveSpecialChar() {
		return haveSpecialChar;
	}

	public void setHaveSpecialChar(boolean haveSpecialChar) {
		this.haveSpecialChar = haveSpecialChar;
	}

	public String exec() {
		long a;
		StringBuffer st = new StringBuffer(nbChar);
		for (int i = 0; i < nbChar; i++) {
			a = Math.round(Math.random() * 2);
			if (a == 0)
				st.append(specialChars.charAt((int) Math.round(Math.random() * specialChars.length())));
			else if (a == 1)
				st.append(letterChars.charAt((int) Math.round(Math.random() * letterChars.length())));
			else if (a == 2)
				st.append(numberChars.charAt((int) Math.round(Math.random() * numberChars.length())));
		}
		return st.toString();
	}
}
