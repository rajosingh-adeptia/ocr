package com.adeptia.ocr;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Rajo
 **         <ul>
 *
 *         <li>Algorithms (such as AES, RSA, MD5 or SHA-1).
 *
 *         <li>Key generation, conversion, and management facilities (such as
 *         for algorithm-specific keys).
 *
 *         </ul>
 */
/**
 * @author Rajo
 *
 */
public class AESEncrypterCustom {
	private static SecretKey aesSecretKey = null;
	private Cipher aesEncryptCipher = null;
	private Cipher aesDecryptCipher = null;
	// This is a key which is used for both encryption/ decryption along with this have used IV key for more security.
	private static String passPhrase = "PinoLacedelli+AchilleCompagnonertyu";

	public AESEncrypterCustom(SecretKey key) {
		Provider sunJce = new com.sun.crypto.provider.SunJCE();
		Security.insertProviderAt(sunJce, 1);

		// Create an 16-byte initialization vector
		byte[] ivKey = { 0x34, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65,
				0x12 };
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
			return new sun.misc.BASE64Encoder().encode(aesEncryptCipher.doFinal(src.getBytes("UTF8")));
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
			// String osName = System.getProperty("os.name");

			src = new String(aesDecryptCipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(src)), "UTF-8");

			return src;
		} catch (Exception e) {
			throw new Exception("Error while decrypting " + e.getMessage(), e);
		}
	}

	/**
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmptyField(String input) {
		return ((input == null) || (input.equals("")));

	}

	public static String AESEncrypter(String rawData) throws Exception {
		if(isEmptyField(rawData)){
			return rawData;
		}
		return new AESEncrypterCustom(getAesBaseSecretKey()).encrypt(rawData);

	}

	public static String AESDecrypter(String encData) throws Exception {
		if(isEmptyField(encData)){
			return encData;
		}
		return new AESEncrypterCustom(getAesBaseSecretKey()).decrypt(encData);

	}
	
	/**
	 * @param ssn
	 * @param isEncrypted
	 * @return
	 */
	public static String ssnMasking(String ssn, boolean isEncrypted){
		try{
			if(isEmptyField(ssn)){
				return ssn;
			}
			 //String input = "456-67-8901";
			if(isEncrypted){
				String ssnNew=AESDecrypter(ssn);
				Pattern p = Pattern.compile("^(\\d{3}[- ]?\\d{2}[- ]?)(\\d{4})$");
			    Matcher m = p.matcher(ssnNew);
			    if (m.matches()) {
			        System.out.println("XXX-XX-" + m.group(2));
			        return "XXX-XX-" + m.group(2);
			    } else {
			        return ssnNew;
			    }
			}
			else{
				Pattern p = Pattern.compile("^(\\d{3}[- ]?\\d{2}[- ]?)(\\d{4})$");
			    Matcher m = p.matcher(ssn);
			    if (m.matches()) {
			        System.out.println("XXX-XX-" + m.group(2));
			        return "XXX-XX-" + m.group(2);
			    } else {
			        return ssn;
			    }
			}
			    
		}
		catch(Exception e){
			//logger.error("ssn Masking having issue for--> "+ssn,e);
			e.printStackTrace();
		}
		return ssn;
	}

	/**
	 * @param args
	 * @throws Exception
	 * This method is used for unit testing.
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String rawData = "989765623";
		//boolean flag = AESEncrypterCustom.isEmptyField("dd");
		//System.out.println(flag);

		//SecretKey key = getAesBaseSecretKey();
		//System.out.println(key.getEncoded());
		//System.out.println(AESEncrypter(rawData));
		//String encData=AESEncrypter(rawData);
		System.out.println(AESEncrypter(rawData));
		System.out.println(ssnMasking("989765623",false));
		System.out.println(ssnMasking("vLHCPb1SKhqb14oSNORZsA==",true));
		//String encData = new AESEncrypterCustom(getAesBaseSecretKey()).encrypt(rawData);
		//System.out.println("Encripted data-- " + encData);
		//String decData = new AESEncrypterCustom(getAesBaseSecretKey()).decrypt(encData);
		//System.out.println("Decripted data-- " + decData);

	}

}
