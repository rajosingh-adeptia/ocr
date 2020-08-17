package com.adeptia.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncrypter {
	private static SecretKey aesSecretKey = null;
	private Cipher aesEncryptCipher = null;
	private Cipher aesDecryptCipher = null;

	public AESEncrypter(SecretKey key) {
		Provider sunJce = new com.sun.crypto.provider.SunJCE();
		Security.insertProviderAt(sunJce, 1);

		// Create an 16-byte initialization vector
		byte[] ivKey = { 0x34, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65,
				0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x12 };
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivKey);
		try {
			aesEncryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesEncryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			aesDecryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesDecryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (NoSuchAlgorithmException e) {
		} catch (NoSuchPaddingException e) {
		} catch (InvalidKeyException e) {
		} catch (InvalidAlgorithmParameterException e) {
		}
	}

	public static SecretKey getAesBaseSecretKey() throws Exception {
		if (aesSecretKey == null) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String passPhrase = "PinoLacedelli+AchilleCompagnonertyu";
			byte[] key = md.digest(passPhrase.getBytes("UTF-8"));
			aesSecretKey = new SecretKeySpec(key, "AES");
		}
		return aesSecretKey;
	}
	
	/**
	 * It encrypts the given string.
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String src) throws Exception {
		try {
			return new sun.misc.BASE64Encoder().encode(aesEncryptCipher
					.doFinal(src.getBytes("UTF8")));
		} catch (Exception e) {
			throw new Exception("Error while encrypting " + e.getMessage(), e);
		}
	}

	/**
	 * It decrypts the given string
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String src) throws Exception {
		try {
			//String osName = System.getProperty("os.name");
			
				src = new String(aesDecryptCipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(src)), "UTF-8");
			
			return src;
		}catch (Exception e) {
			throw new Exception("Error while decrypting " + e.getMessage(), e);
		}
	}


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String rawData="rajo222222222222";
		SecretKey key=getAesBaseSecretKey();
		System.out.println(key.getEncoded());
		String encData=new AESEncrypter(getAesBaseSecretKey()).encrypt(rawData);
		System.out.println("Encripted data-- "+encData);
		String decData=new AESEncrypter(getAesBaseSecretKey()).decrypt(encData);
		System.out.println("Decripted data-- "+decData);
		
	}

}
