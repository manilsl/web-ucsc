package com.devicemgt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncript {
	
	private static MessageDigest md;
	
	public static String getEncriptedPassword(String password) {
		try {
			md = MessageDigest.getInstance("SHA");
            byte[] passBytes = password.getBytes();

            md.reset();

            byte[] md5Digest = md.digest(passBytes);

            StringBuffer myStringBuffer = new StringBuffer();

            for (int i = 0; i < md5Digest.length; i++) {
                myStringBuffer.append(Integer.toHexString(0xff & md5Digest[i]));
            }
            return myStringBuffer.toString();

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;

    }

}
